package woowacourse.auth.ui;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.TOKEN;
import static woowacourse.helper.restdocs.RestDocsUtils.getRequestPreprocessor;
import static woowacourse.helper.restdocs.RestDocsUtils.getResponsePreprocessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.helper.restdocs.RestDocsTest;

@DisplayName("Auth 컨트롤러 단위테스트")
public class AuthControllerTest extends RestDocsTest {

    @DisplayName("로그인에 성공한다.")
    @Test
    void login() throws Exception {
        TokenRequest request = new TokenRequest(EMAIL, PASSWORD);

        TokenResponse expectResponse = new TokenResponse(TOKEN);
        given(authService.generateToken(ArgumentMatchers.any(TokenRequest.class)))
                .willReturn(expectResponse);

        ResultActions resultActions = mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectResponse)));

        // docs
        resultActions.andDo(document("auth-login",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestFields(
                        fieldWithPath("email").type(STRING).description("이메일"),
                        fieldWithPath("password").type(STRING).description("비밀번호")
                ),
                responseFields(
                        fieldWithPath("accessToken").type(STRING).description("토큰")
                )));
    }

    @DisplayName("이메일 형식으로 인해 로그인에 실패한다.")
    @Test
    void loginEmailNotRight() throws Exception {
        final TokenRequest request = new TokenRequest("Huni", PASSWORD);
        final ErrorResponse response = new ErrorResponse("올바른 이메일 형식으로 입력해주세요.");

        mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }


    @DisplayName("비밀번호 공백으로 인해 로그인에 실패한다.")
    @Test
    void loginPasswordNotRight() throws Exception {
        final TokenRequest request = new TokenRequest(EMAIL, " ");
        final ErrorResponse response = new ErrorResponse("비밀번호에는 공백이 허용되지 않습니다.");

        mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }
}

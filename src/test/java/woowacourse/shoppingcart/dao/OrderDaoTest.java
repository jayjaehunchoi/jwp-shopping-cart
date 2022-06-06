package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import woowacourse.helper.fixture.MemberFixture;
import woowacourse.member.dao.MemberDao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderDaoTest {

    private final OrderDao orderDao;
    private final MemberDao memberDao;

    public OrderDaoTest(DataSource dataSource) {
        this.orderDao = new OrderDao(dataSource);
        this.memberDao = new MemberDao(dataSource);
    }

    @DisplayName("Order를 추가하는 기능")
    @Test
    void addOrders() {
        final Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        final Long orderId = orderDao.save(id);

        assertThat(orderId).isNotNull();
    }

    @DisplayName("Order가 해당 멤버의 주문인지 확인")
    @Test
    void isValid() {
        final Long id = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        final Long orderId = orderDao.save(id);

        assertThat(orderDao.isValidOrderId(id, orderId)).isTrue();
    }
}

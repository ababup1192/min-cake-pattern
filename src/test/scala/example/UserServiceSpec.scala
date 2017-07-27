package example

import org.scalatest._
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._


class UserServiceSpec extends FlatSpec with Matchers with MockitoSugar {

  trait SetUp {
    self =>

    val registeredUser = User("John", 15)
    val userRepository: UserRepository = mock[UserRepository]

    val userService = new UserService {
      val userRepository: UserRepository = self.userRepository
    }
    when(userRepository.store(registeredUser)).thenReturn(registeredUser)
  }

  "UserService" should "signUp successful" in new SetUp {

    private[this] val signedUpUser = userService.signUp(registeredUser)

    signedUpUser shouldEqual Right(registeredUser)
    verify(userRepository, times(1)).store(registeredUser)
  }
}

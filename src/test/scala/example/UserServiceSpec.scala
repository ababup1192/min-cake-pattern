package example

import org.scalatest._
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._


class UserServiceSpec extends FlatSpec with Matchers with MockitoSugar {

  trait SetUp {
    self =>

    val userRepository: UserRepository = mock[UserRepository]

    val userService = new UserService {
      val userRepository: UserRepository = self.userRepository
    }
  }

  "UserService" should "signUp successful" in new SetUp {
    val registeredUser = User("John", 15)
    when(userRepository.store(registeredUser)).thenReturn(registeredUser)
    private[this] val signedUpUser = userService.signUp(registeredUser)

    signedUpUser shouldEqual Right(registeredUser)
    verify(userRepository, times(1)).store(registeredUser)
  }

  "UserService" should "signUp failed when user's age is less than 15." in new SetUp {
    val registeredUser = User("John", 14)

    userService.signUp(registeredUser) shouldEqual
      Left("Registration is impossible unless your age is 15 years or older.")
  }

  "UserService" should "signUp failed when initial of user's name is not 'J'" in new SetUp {
    val registeredUser = User("john", 15)

    userService.signUp(registeredUser) shouldEqual
      Left(s"The initial letter of your name is '${registeredUser.name(0)}'." +
        s" But, the initial letter of the name must be 'J'.")
  }
}

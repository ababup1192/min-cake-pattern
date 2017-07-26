package example

trait UserService extends UsesUserRepository {
  def all: Seq[User]

  def signUp(user: User): Either[String, User]

  def signIn(name: String): Either[String, User]
}


object UserService extends UserService with MixInRepository {
  override def all: Seq[User] = userRepository.all

  override def signUp(user: User): Either[String, User] = {
    if (user.age < 15) {
      Left("Registration is impossible unless your age is 15 years or older.")
    } else if (user.name(0) != 'J') {
      Left(s"The initial letter of your name is '${user.name(0)}'." +
        s" But, the initial letter of the name must be 'J'.")
    } else {
      userRepository.store(user)
      Right(user)
    }
  }

  override def signIn(name: String): Either[String, User] =
    userRepository.resolveBy(name) match {
      case Some(user) => Right(user)
      case _ => Left(s"User: $name is Not Found.")
    }
}


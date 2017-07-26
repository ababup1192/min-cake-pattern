package example

trait UsesUserRepository {
  val userRepository: UserRepository
}

trait UserRepository {
  def all: Seq[User]

  def store(user: User): User

  def resolveBy(name: String): Option[User]
}

trait MixInRepository extends UsesUserRepository {
  val userRepository = UserRepositoryImpl
}

object UserRepositoryImpl extends UserRepository {
  private[this] var repository = Seq[User]()

  override def all: Seq[User] = repository

  override def store(user: User): User = {
    repository = repository :+ user
    user
  }

  override def resolveBy(name: String): Option[User] = repository.find(_.name == name)
}
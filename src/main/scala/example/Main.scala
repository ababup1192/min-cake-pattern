package example

object Main extends App {
  val service = UserService

  println(service.all)

  println(service.signUp(User("John", 12)))
  println(service.signUp(User("hoge", 15)))
  println(service.signUp(User("John", 15)))

  println(service.all)

  println(service.signIn("Mike"))
  println(service.signIn("John"))
}


package u09.examples

import u09.model.QMatrix

object TryQMatrix extends App :

  import u09.model.QMatrix.Move.*
  import u09.model.QMatrix.*

  val rl: QMatrix.Facade = Facade(
    width = 10,
    height = 10,
    initial = (0,0),
    terminal = { case _=> false },
    reward = { case ((1,0),_) => 10; case ((3,0),_) => 5; case ((0, _), LEFT) => -1; case ((_, 0), UP) => -1; case ((9, _), RIGHT) => -1; case ((_, 9), DOWN) => -1; case _ => 0 },
    jumps = { case ((1,0),_) => (1,4); case ((3,0),_) => (3,2) },
    gamma = 0.9,
    alpha = 0.7,
    epsilon = 0.6,
    v0 = 1
  )

  val q0 = rl.qFunction
  println(rl.show(q0.vFunction,"%2.2f"))
  val q1 = rl.makeLearningInstance().learn(30000,400,q0)
  println(rl.show(q1.vFunction,"%2.2f"))
  println(rl.show(s => q1.bestPolicy(s).toString,"%7s"))
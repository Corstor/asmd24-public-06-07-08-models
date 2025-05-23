package scala.u09.tasks.qlearningdesign

import u09.model.QMatrix
import QMatrix.*
import Move.*

object Corridor extends App :
  val rl: QMatrix.Facade = Facade(
    width = 10,
    height = 10,
    initial = (0,0),
    terminal = { case _ => false },
    reward = { case ((9, 9), _) => 100; case ((3, 3), _) => -1000; case ((5, 7), _) => -1000; case ((0, _), LEFT) => -1; case ((_, 0), UP) => -1; case ((9, _), RIGHT) => -1; case ((_, 9), DOWN) => -1; case _ => 0 },
    jumps = Map.empty,
    gamma = 0.9,
    alpha = 0.7,
    epsilon = 0.6,
    epsilonReducer = 0.00002,
    v0 = 1
  )

  val q0 = rl.qFunction
  println(rl.show(q0.vFunction,"%2.2f"))
  val q1 = rl.makeLearningInstance().learn(30000,400,q0)
  println(rl.show(q1.vFunction,"%2.2f"))
  println(rl.show(s => q1.bestPolicy(s).toString,"%7s"))

object Collector extends App:
  val rl: QMatrix.Facade = Facade(
    width = 10,
    height = 10,
    initial = (0,0),
    terminal = { case _ => false },
    reward = { case ((3, 4), _) => 10; case ((1, 7), _) => 10; case ((9, 9), _) => 10; case ((6, 3), _) => 10; case ((0, _), LEFT) => -1; case ((_, 0), UP) => -1; case ((9, _), RIGHT) => -1; case ((_, 9), DOWN) => -1; case _ => 0 },
    jumps = { case ((3, 4), _) => (0, 0); case ((1, 7), _) => (0, 0); case ((9, 9), _) => (0, 0); case ((6, 3), _) => (0, 0); },
    gamma = 0.9,
    alpha = 0.7,
    epsilon = 0.8,
    epsilonReducer = 0.00001,
    v0 = 1
  )

  val q0 = rl.qFunction
  println(rl.show(q0.vFunction,"%2.2f"))
  val q1 = rl.makeLearningInstance().learn(30000,400,q0)
  println(rl.show(q1.vFunction,"%2.2f"))
  println(rl.show(s => q1.bestPolicy(s).toString,"%1s"))
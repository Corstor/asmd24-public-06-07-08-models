package scala.u06.modelling

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class PNReadersWritersSpec extends AnyFlatSpec:

  import u06.examples.PNReadersWriters.*
  import u06.examples.PNReadersWriters.Place.*

  "PN for readers-writers" should "properly generate next when a reader is reading" in:
    pnRW.next(MSet(T_ACCESS, READ)) shouldBe Set(MSet(T_ACCESS, IDLE))

  it should "properly generate next when a writer is writing" in:
    pnRW.next(MSet(WRITE)) shouldBe Set(MSet(T_ACCESS, IDLE))

  it should "properly generate next when a token is in idle" in:
    pnRW.next(MSet(IDLE)) shouldBe Set(MSet(ASK))

  it should "properly generate next when a token is asking" in:
    pnRW.next(MSet(ASK)) shouldBe Set(MSet(T_READ), MSet(T_WRITE))

  it should "properly generate next when a token is trying to read" in:
    pnRW.next(MSet(T_READ)) shouldBe Set()
    pnRW.next(MSet(T_READ, T_ACCESS)) shouldBe Set(MSet(T_ACCESS, READ))

  it should "properly generate next when a token is trying to write" in:
    pnRW.next(MSet(T_WRITE)) shouldBe Set()
    pnRW.next(MSet(T_WRITE, T_ACCESS)) shouldBe Set(MSet(WRITE))
    pnRW.next(MSet(T_WRITE, T_ACCESS, READ)) shouldBe Set(MSet(T_ACCESS, IDLE, T_WRITE))

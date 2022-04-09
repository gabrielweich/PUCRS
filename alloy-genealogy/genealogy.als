open util/ordering [Tempo] as T

sig Tempo {}

abstract sig Pessoa {
  filhos: Pessoa set -> Tempo,
  conjuge: Pessoa lone -> Tempo,
  vivo: set Tempo
}

sig Homem, Mulher extends Pessoa {}

fun pais [t: Tempo]: Pessoa -> Pessoa {
  ~(filhos.t)
}

fun netos [t: Tempo]: Pessoa -> Pessoa {
  filhos.t.filhos.t
}

fun descendentes [t: Tempo]: Pessoa -> Pessoa {
  ^(filhos.t)
}

fun avos [t: Tempo]: Pessoa -> Pessoa {
  ~(filhos.t.filhos.t)
}

fun irmaos [t: Tempo]: Pessoa -> Pessoa {
  pais[t].filhos.t
}

fun tios [t: Tempo]: Pessoa -> Pessoa {
  pais[t].(irmaos[t])
}

fun primos [t: Tempo]: Pessoa -> Pessoa {
  tios[t].filhos.t
}


pred NaoMudaFilhos [ps: set Pessoa, t, t': Tempo] {
  all p: ps | p.filhos.t' = p.filhos.t
}

pred NaoMudaConjuges [ps: set Pessoa, t, t': Tempo] {
  all p: ps | p.conjuge.t' = p.conjuge.t
}

pred NaoMudaVivo [ps: set Pessoa, t, t': Tempo] {
  all p: ps | p in vivo.t' iff p in vivo.t
}

pred Nascer [p: Pessoa, m: Homem, w: Mulher, t,t': Tempo] {
  m+w in vivo.t
  p !in vivo.t

  m.conjuge.t = w
  w.conjuge.t = m

  vivo.t' = vivo.t + p
  m.filhos.t' = m.filhos.t + p
  w.filhos.t' = w.filhos.t + p

  NaoMudaFilhos[Pessoa - m - w, t, t']
  NaoMudaConjuges[Pessoa, t, t']
  NaoMudaVivo[Pessoa - p, t, t']
}

pred Casar [m: Homem, w: Mulher, t, t': Tempo] {
  m+w in vivo.t
  m.conjuge.t = none
  w.conjuge.t = none

  m.conjuge.t' = w
  w.conjuge.t' = m

  NaoMudaFilhos[Pessoa, t, t']
  NaoMudaConjuges[Pessoa - w - m, t, t']
  NaoMudaVivo[Pessoa, t, t']
}

pred Divorciar [m: Homem, w: Mulher, t, t': Tempo] {
  m+w in vivo.t

  m.conjuge.t = w
  w.conjuge.t = m

  m.conjuge.t' = none
  w.conjuge.t' = none

  NaoMudaFilhos[Pessoa, t, t']
  NaoMudaConjuges[Pessoa - w - m, t, t']
  NaoMudaVivo[Pessoa, t, t']
}


fact naoPodeCasarConsigoMesmo {
  all t: Tempo { no p:Pessoa | p.conjuge.t = p }
}

fact naoPodeCasarComDescendente {
  all t: Tempo { no p:Pessoa | p.conjuge.t in p.^(filhos.t) iff p.conjuge.t != none }
}

fact naoPodeSerFilhoDeDescendente {
  all t: Tempo { no p:Pessoa | p in p.^(filhos.t) }
}

fact casamentoReciproco {
  all t: Tempo {
    all p: Pessoa | p.conjuge.t.conjuge.t = p iff p.conjuge.t != none
  }
}

fact casamentoHomemMulher {
  all t: Tempo {
    all p: Pessoa | let c = p.conjuge |
      (p in Homem implies c.t in Mulher) and
      (p in Mulher implies c.t in Homem)
    }
}


assert assertNetos {all t:Tempo {all p: netos[t] | p in avos[t][p].filhos.t.filhos.t}}
assert assertPrimos {all t:Tempo {all p: primos[t] | p in tios[t][p].filhos.t}}
assert assertIrmaos {all t:Tempo {all p: irmaos[t] | p in pais[t][p].filhos.t}}
assert assertIrmaosDescendentesPai {all t: Tempo {all p: irmaos[t] | p in pais[t][p].(descendentes[t]) }}

check assertNetos for 8
check assertIrmaos for 8
check assertPrimos for 8
check assertIrmaosDescendentesPai for 8


one sig P1 extends Homem {}
one sig P2 extends Mulher {}
one sig P3 extends Homem {}


pred execCasarNascer [t: Tempo] {
  P1 + P2 in vivo.t
  P3 !in vivo.t

  P1.conjuge.t = none
  P2.conjuge.t = none

  Casar[P1, P2, t, T/next[t]]
  Nascer[P3, P1, P2, T/next[t], T/next[T/next[t]]]
}


pred execCasarDivorciar [t: Tempo] {
  P1 + P2 in vivo.t

  P1.conjuge.t = none
  P2.conjuge.t = none

  Casar[P1, P2, t, T/next[t]]
  Divorciar[P1, P2, T/next[t], T/next[T/next[t]]]
}



run {execCasarNascer[T/first]} for 3
run {execCasarDivorciar[T/first]} for 3


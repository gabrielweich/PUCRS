"""
    Solução para simular um escalonador de processos através
    do algoritmo Round-robin.

    Data: 04/10/2018
    Autores: Angelo Calebe e Gabriel Weich
    Linguagem: Python 3.7
"""


from dataclasses import dataclass, field
from collections import defaultdict, deque
from typing import List
import heapq
import sys


@dataclass
class Process:
    """
    Classe que representa o processo.

    Atributos:
        index		    Índice (número do processo)
        arrival	        Momento que o processo chegou
        execution	    Tempo de execução
        priority	    Prioridade (quanto menor o valor maior a prioridade)
        io_times	    Lista com os momentos em que o processo realiza operações E/S
        turn_time	    Tempo que o processo já executou da fatia de tempo disponível para aquele turno
        remaining	    Tempo restante para o processo finalizar a execução
        blocked_time    Tempo que o processo está na fila de bloqueados
    """

    def __init__(self, index: str, arrival: int, execution: int, priority: int, io_times: List[int] = field(default_factory=list)):
        self.index = index
        self.arrival = arrival
        self.execution = execution
        self.priority = priority
        self.io_times = io_times
        self.turn_time = 0
        self.remaining = self.execution
        self.blocked_time = 0

    def __lt__(self, other):
        return self.priority < other.priority

    def __le__(self, other):
        return self.priority <= other.priority

    def __gt__(self, other):
        return self.priority > other.priority

    def __ge__(self, other):
        return self.priority >= other.priority

    def __str__(self):
        return str(self.__dict__)


@dataclass
class CPU:
    """
    Classe que representa o processador.

    Atributos:
        process		    Processo que está sendo executado
        time	        Contador de tempo
    """
    process: Process = None
    time: int = field(init=False, default=0)

    def cycle(self):
        if self.process:
            self.process.remaining -= 1
            self.process.turn_time += 1

        self.time += 1

    def put(self, process):
        self.time += 1
        self.process = process

    def remove(self):
        process = self.process
        self.process = None
        return process


@dataclass
class Scheduler:
    """
    Classe que representa o escalonador de processos.

    Parâmetros:
        processes       Lista de processos aguardando
        quantum         Fatia de tempo
        cpu             Processador
        ready_queue     Fila de processos prontos para executar (heap)
        blocked_list	Lista de processos bloqueados. Processos que fazem operações de E/S são inseridos aqui
        block_time		Tempo de uma operação de E/S
    """

    processes: List[Process]
    quantum: int
    cpu: CPU = field(init=False, default=CPU())
    ready_queue: heapq = field(init=False, default_factory=list)
    blocked_list: List[Process] = field(init=False, default_factory=list)
    block_time: int = 4

    def run(self) -> List[str]:
        process_count = len(self.processes)
        response_time = 0
        waiting_time = 0
        blocked_time = 0
        result = []
        queues = defaultdict(deque)

        # Executa o escalonador enquanto houver algum processo na fila ou no processador
        while len(self.processes) > 0 or len(self.ready_queue) > 0 or self.cpu.process or len(self.blocked_list) > 0:

            # Percorre a lista de processos bloqueados
            for i, p in enumerate(self.blocked_list):
                blocked_time += 1
                # Caso o processo acabou de realizar I/O é colocado na file de prontos para executar
                if p.blocked_time == self.block_time - 2:
                    p.blocked_time = 0
                    heapq.heappush(self.ready_queue, p)
                    queues[p.priority].append(p)
                    del self.blocked_list[i]
                    blocked_time += 1
                else:
                    p.blocked_time += 1

            # Chegou o momento de um processo entrar na fila

            while len(self.processes) > 0 and self.processes[0].arrival <= self.cpu.time:
                p = self.processes.pop(0)
                heapq.heappush(self.ready_queue, p)
                queues[p.priority].append(p)

            switch = False

            # já existe um processo executando
            if self.cpu.process:

                # O precesso terminou de executar
                if self.cpu.process.remaining == 0:
                    p = self.cpu.remove()
                    waiting_time += self.cpu.time - p.arrival - p.execution
                    switch = True

                # Acabou a fatia de tempo para o processo
                elif self.cpu.process.turn_time == self.quantum:
                    self.cpu.process.turn_time = 0
                    p = self.cpu.remove()
                    heapq.heappush(self.ready_queue, p)
                    queues[p.priority].append(p)
                    switch = True

                # Um processo com maior prioridade está pronto
                elif len(self.ready_queue) > 0 and self.cpu.process > self.ready_queue[0]:
                    p = self.cpu.remove()
                    heapq.heappush(self.ready_queue, p)
                    queues[p.priority].append(p)
                    switch = True

                # O processo irá realizar uma operação de E/S
                elif len(self.cpu.process.io_times) > 0 and self.cpu.process.io_times[0] == self.cpu.process.execution - self.cpu.process.remaining:
                    del self.cpu.process.io_times[0]
                    self.blocked_list.append(self.cpu.remove())
                    switch = True

            # O processador está desocupado e existem processos na fila
            elif len(self.ready_queue) > 0:
                switch = True

            # Realiza a troca do processo, inserindo aquele com maior prioridade
            if switch:
                # result.append("C")
                result.append("C")
                if len(self.ready_queue) > 0:
                    p = queues[self.ready_queue[0].priority].popleft()
                    heapq.heappop(self.ready_queue)
                    self.cpu.put(p)
                    if p.remaining == p.execution:
                        r = self.cpu.time - p.arrival
                        response_time += r

            # Não existem processos prontos nem executando
            if not self.cpu.process:
                result.append("-")

            # O processo executou um ciclo
            else:
                result.append(self.cpu.process.index)

            self.cpu.cycle()

        print("Tempo de espera médio: " +
              str((waiting_time-blocked_time)/process_count))
        print("Tempo de resposta médio: " + str(response_time/process_count))

        return result


def read_file(filename) -> Scheduler:
    processes = []

    with open(filename, "r") as f:

        process_count = int(f.readline())
        quantum = int(f.readline())

        for i in range(process_count):
            line = f.readline().split(" ")
            arrival = int(line[0])
            execution = int(line[1])
            priority = int(line[2])
            io_times = [int(io) for io in line[3:]]

            processes.append(
                Process(str(i+1), arrival, execution, priority, io_times))

    return Scheduler(processes, quantum)


if __name__ == "__main__":
    scheduler = read_file(sys.argv[1])
    print("".join(scheduler.run()))

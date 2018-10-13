import sys
import os
from graph import Graph
from node import Node
from execute_dfs import ExecuteDfs

if __name__ == "__main__":
    graph = Graph()
    initial_node = set()
    with open(os.path.abspath("test_files/%s" % sys.argv[1]), 'r') as f:
        lista_arquivo = f.read().splitlines()

    for vertice in lista_arquivo[1:int(lista_arquivo[0])+1]:
        line = vertice.split(' ')
        task = line[0]
        value = line[1]
        initial_node.add(task)
        graph.add_node(Node(task, value))
        
    for aresta in lista_arquivo[int(lista_arquivo[0])+2:]:
        line = aresta.split(' ')
        task1 = line[0]
        task2 = line[1]
        initial_node.discard(task2)
        repetitions = line[2]
        nodo = graph.get_node(task1)
        nodo.add_linked_task(task2, repetitions)

    graph.initial_task = next(iter(initial_node))

    print(ExecuteDfs(graph).calculate_total_cost())

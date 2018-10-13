class ExecuteDfs:
    def __init__(self, graph):
        self.graph = graph

    def calculate_total_cost(self):
        return self.calculate_total_cost_handler(self.graph.initial_task)

    def calculate_total_cost_handler(self, task):
        node = self.graph.get_node(task)
        if node.visited: return node.calculated_cost
        node.cicle_status = 1
        for linked_task in node.linked_tasks:
            if self.graph.get_node(linked_task).cicle_status == 1: raise Exception("Graph has cicle")
            node.calculated_cost += node.get_repetitions(linked_task)*self.calculate_total_cost_handler(linked_task)
        node.cicle_status = 2
        node.visited = True
        return node.calculated_cost
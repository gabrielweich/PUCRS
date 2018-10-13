class Graph:
    def __init__(self):
        self.graph_dict = dict()
        self.initial_task = None

    def add_node(self, node):
        self.graph_dict[node.task] = node

    def get_node(self, task):
        return self.graph_dict[task]

    def __str__(self):
        str = "initial node: %s\n" % self.initial_task
        for k, v in self.graph_dict.iteritems():
            str += "%s : %s\n\n" % (k, v)

        return str
class Node:
    def __init__(self, task, value):
        self.task = task
        self.value = int(value)
        self.linked_tasks = dict()
        self.calculated_cost = int(value)
        self.visited = False
        self.cicle_status = 0
    
    def add_linked_task(self, task, repetitions):
        self.linked_tasks[task] = int(repetitions)

    def get_repetitions(self, task):
        return self.linked_tasks[task]

    def __str__(self):
        str = "\n"
        for k, v in self.linked_tasks.iteritems():
            str += "\t%s : %s\n" % (k, v)

        return str
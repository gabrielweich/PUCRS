public class LimitLinkedList {

    private Node head;

    private class Node {
        public int start;
        public int end;
        public Node next;

        public Node(int start, int end) {
            this.start = start;
            this.end = end;
            this.next = null;
        }

    }

    public int add(int start, int end) {

        if(this.head == null) {
            this.head = new Node(start, end);
            return end - start;
        }

        Node node = this.head;
        Node aux = this.head;
        int area = 0;

        while(node.next != null) {
            if(node.end > start) break;
            aux = node;
            node = node.next;
        }

        if(node.start <= start && node.end >= end) return 0; //Se o intervalo encontra-se dentro de outro intervalo

        if(node.start > end) { //se o nodo não está dentro do próximo intervalo
            if(node == this.head) { //se o nodo será inserido no início
                this.head = new Node(start, end);
                this.head.next = aux;
            }

            else{
                aux.next = new Node(start, end);
                aux.next.next = node;
            }

            return end - start;

        }

        if(node.end < start) {
            node.next = new Node(start, end);
            return end - start;
        }

        if(node.start >= start) {
            area += node.start - start;
            node.start = start;
        }

        Node interval = node;
        while(node.next != null) {
            if(node.end > end) break;
            if(interval != node) area += node.start - aux.end;
            aux = node;
            node = node.next;
        }

        if(node.start > end) {
            area += end - aux.end;
            interval.end = end;
            interval.next = node;
            return area;
        }

        if(interval != node) area += node.start - aux.end;
        interval.next = node.next;

        if(node.end <= end) {
            area += end - node.end;
            interval.end = end;
        }

        else interval.end = node.end;

        return area;

    }

}

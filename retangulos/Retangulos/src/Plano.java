public class Plano {
    private LimitLinkedList[] xArray;
    public Cores cores;

    public Plano(int maxSize){
        cores = new Cores();
        xArray = new LimitLinkedList[maxSize];
    }



    public void addNodo(Retangulo retangulo){
        for(int i = retangulo.getX1(); i < retangulo.getX2(); i++){
            if(xArray[i] != null){
                cores.calc(retangulo.getCor(), xArray[i].add(retangulo.getY1(), retangulo.getY2()));
            }
            else{
                LimitLinkedList newList = new LimitLinkedList();
                int aaa = newList.add(retangulo.getY1(), retangulo.getY2());
                cores.calc(retangulo.getCor(), aaa);
                xArray[i] = newList;
            }
            //System.out.println(i);
            //xArray[i].print();

        }
    }

    public void printCores(){
        System.out.println(cores.toString());
        System.out.println(cores.getTotal());
    }
}
package adam0brien.pcbhelper;

public class DisjointSetTest {

    public static void main(String[] args) {
        DisjointSetNode<Integer> a=new DisjointSetNode<>(8);
        DisjointSetNode<Integer> b=new DisjointSetNode<>(6);
        DisjointSetNode<Integer> c=new DisjointSetNode<>(2);
        DisjointSetNode<Integer> d=new DisjointSetNode<>(5);
        b.parent=a;
        c.parent=a;
        d.parent=c;
        System.out.println(c.getParent().getData());  // c's parent is a, so 2 -> 8


        System.out.println("The root of " + c.data + " is " + find2(c).data);

        int[] dset={10,11,11,2,8,13,2,7,8,9,11,11,7,8};
//        for(int id=0;id<dset.length;id++)
//            System.out.println("The root of "+id+" is "+
//                    find(dset,id));



        System.out.println("Before Union (of Sets with Elements 3 and 4)\n-------------------------------------------");
        for(int id=0;id<dset.length;id++)
            System.out.println("The root of element "+id+" is "+find(dset,id)+" (element value: "+dset[id]+")");
        union(dset,3,4); //Union disjoint sets containing elements with ids 3 and 4
        System.out.println("\nAfter Union\n-------------------------------------------");
        for(int id=0;id<dset.length;id++)
            System.out.println("The root of element "+id+" is "+find(dset,id)+" (element value: "+dset[id]+")");
    }





    //Recursive version of find
    public static int find(int[] a, int id) {
        if(a[id]==id) return id;
        else return find(a,a[id]);
    }


    //Recursive version of find
    public static DisjointSetNode<?> find2(DisjointSetNode<?> node){
        if(node.parent==null) return node;
        else return find2(node.parent);
    }


    //Quick union of disjoint sets containing elements p and q (Version 1)
    public static void union(int[] a, int p, int q) {
        a[find(a,q)]=p; //The root of q is made reference p
    }

}

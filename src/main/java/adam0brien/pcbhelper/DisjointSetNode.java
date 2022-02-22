package adam0brien.pcbhelper;

public class DisjointSetNode<T> {
    public DisjointSetNode<?> parent=null;
    public T data;
    public DisjointSetNode(T data) {
        this.data=data;
    }

    public DisjointSetNode<?> getParent() {
        return parent; //maybe null?
    }

    public void setParent(DisjointSetNode<?> parent) {
        this.parent = parent;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

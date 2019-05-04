package com.oast;

public class Node {
    private int id;

    public Node(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Node)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Node n = (Node) o;

        if(this.id == n.id)
            return true;
        else
            return false;
    }
}
// Authors: Bolivar Pereira, Eduardo Ferreira, Gabriel Brunichaki, Gabriel Weich

class {:autocontracts} Stack {
    var a:array<nat>;
    var tail:nat;
    ghost var Content:seq<nat>;
    
    predicate Valid(){
        a.Length > 0
        && 0 <= tail <= a.Length
        && Content == a[0..tail]
        && tail == |Content|
    }

    constructor(size:nat)
    requires size > 0
    ensures a.Length == size
    ensures Content == []
    {
        a := new nat[size];
        tail := 0;

        Content := [];
    }

    method Length() returns (n:nat)
    ensures n == |Content|
    {
        return tail;
    }

    method maxSize() returns(n:nat)
    ensures n == a.Length
    {
        return a.Length;
    }

    method Push(element:nat) returns(success: bool)
    ensures a == old(a)
    ensures success == (old(tail) < a.Length)
    ensures success == false ==> Content == old(Content)
    ensures success == true ==> Content == old(Content) + [element]
    {
        if tail == a.Length {
            success := false;
        } else {
            success := true;
            a[tail] := element;
            tail := tail + 1;
            Content := a[0..tail];
        }
    }

    method Pop() returns (element:nat)
    requires |Content| > 0
    ensures a == old(a)
    ensures tail == old(tail) - 1
    ensures element == old(Content)[tail]
    ensures Content == old(Content)[0..tail]
    {
        element := a[tail - 1];
        tail := tail - 1;
        Content := a[0..tail];
    }


    method isEmpty() returns (empty:bool)
    ensures empty == (|Content| == 0)
    {
        return (tail == 0);
    }

    method isFull() returns (full:bool)
    ensures full == (|Content| == a.Length)
    {
        return (tail == a.Length);
    }

    predicate Reversed(a:seq<nat>, b:seq<nat>)
    requires |a| == |b|
    {
        forall i :: 0 <= i < |a| ==> a[i] == b[|a| - 1 - i]
    }

    predicate permutation(a:seq<int>, b:seq<int>)
    {
        multiset(a) == multiset(b)
    }

    method Reverse()
    ensures |Content| == |old(Content)|
    ensures Reversed(Content, old(Content))
    ensures tail == old(tail)
    {
        if (tail > 1)
        {
            var i:= 0;
            var outarr := new nat[a.Length];
            forall (i | 0 <= i < tail) { 
                outarr[tail - i - 1] := a[i]; 
            }

            a := outarr;
            Content := a[0..tail];
        }
    }


    lemma ReversedTest()
    {
        var a := [1, 3, 2];
        var b := [2, 3, 1];
        assert a[0] == 1 && a[1] == 3 && a[2] == 2;
        assert b[0] == 2 && b[1] == 3 && b[2] == 1;
        assert Reversed(a, b);
    }

}



method Main(){
    var stack := new Stack(2);

    var empty := stack.isEmpty();
    assert empty == true;

    var full := stack.isFull();
    assert full == false;

    var firstSuccessful := stack.Push(1);
    assert firstSuccessful == true;

    var secondSuccessful := stack.Push(2);
    assert secondSuccessful == true;

    empty := stack.isEmpty();
    assert empty == false;

    full := stack.isFull();
    assert full == true;


    var thirdSuccessful := stack.Push(3);
    assert thirdSuccessful == false;


    var top := stack.Pop();
    assert top == 2;

    var length := stack.Length();
    assert length == 1;

    var maxSize := stack.maxSize();
    assert maxSize == 2;

    var qSuccessful := stack.Push(2);

    stack.Reverse();

    var rtop := stack.Pop();
    assert rtop == 1;
}
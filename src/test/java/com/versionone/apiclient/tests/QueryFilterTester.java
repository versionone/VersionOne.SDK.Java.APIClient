package com.versionone.apiclient.tests;

import com.versionone.apiclient.*;
import org.junit.Assert;
import org.junit.Test;
import com.versionone.Oid;

public class QueryFilterTester extends MetaTesterBase {
    @Override
    protected String getMetaTestKeys() {
        return "QueryFilterTester";
    }

    @Test
    public void nullToken() throws V1Exception {
        Assert.assertEquals(null, new NoOpTerm().getToken());
        Assert.assertEquals(null, new AndFilterTerm().getToken());
        Assert.assertEquals(null, new OrFilterTerm().getToken());
        Assert.assertEquals(null, new FilterTerm(getWorkitemToDo()).getToken());
    }

    @Test
    public void toToken() throws Exception {
        FilterTerm term = new FilterTerm(getWorkitemScope());
        term.equal(new Object[]{getScopeOid(2)});
        Assert.assertEquals("Workitem.Scope='Scope%3A2'", term.getToken());
    }

    @Test
    public void toToken2() throws Exception {
        FilterTerm term1 = new FilterTerm(getWorkitemParent());
        term1.equal(new Object[]{getThemeOid(48)});

        FilterTerm term2 = new FilterTerm(getWorkitemScope());
        term2.equal(new Object[]{getScopeOid(2)});

        Assert.assertEquals("(Workitem.Parent='Theme%3A48';Workitem.Scope='Scope%3A2')",
                new AndFilterTerm(new IFilterTerm[]{term1, term2}).getToken());
    }

    @Test
    public void complex1() throws Exception {
        FilterTerm termA = new FilterTerm(getWorkitemParent());
        termA.equal(Oid.fromToken("Theme:5", getMeta()));
        termA.equal(Oid.fromToken("Theme:6", getMeta()));

        FilterTerm termB = new FilterTerm(getWorkitemScope());
        termB.equal(Oid.fromToken("Scope:0", getMeta()));

        FilterTerm termC = new FilterTerm(getWorkitemParent());
        termC.notEqual(Oid.fromToken("Theme:7", getMeta()));
        termC.notEqual(Oid.fromToken("Theme:8", getMeta()));

        FilterTerm termD = new FilterTerm(getWorkitemScope());
        termD.notEqual(Oid.fromToken("Scope:1", getMeta()));

        AndFilterTerm and1 = new AndFilterTerm(termA, termB);
        AndFilterTerm and2 = new AndFilterTerm(termC, termD);

        OrFilterTerm o = new OrFilterTerm(and1, and2);

        Assert.assertEquals("((Workitem.Parent='Theme%3A5','Theme%3A6';Workitem.Scope='Scope%3A0')|(Workitem.Parent!='Theme%3A7','Theme%3A8';Workitem.Scope!='Scope%3A1'))",
                o.getToken());
    }

    @Test
    public void complex2() throws Exception {
        FilterTerm termA = new FilterTerm(getWorkitemToDo());
        termA.greater(5);

        FilterTerm termB = new FilterTerm(getWorkitemToDo());
        termB.less(10);

        FilterTerm termC = new FilterTerm(getWorkitemToDo());
        termC.greaterOrEqual(20);

        FilterTerm termD = new FilterTerm(getWorkitemToDo());
        termD.lessOrEqual(30);

        AndFilterTerm and1 = new AndFilterTerm(termA, termB);
        AndFilterTerm and2 = new AndFilterTerm(termC, termD);

        OrFilterTerm o = new OrFilterTerm(and1, and2);

        Assert.assertEquals("((Workitem.ToDo>'5';Workitem.ToDo<'10')|(Workitem.ToDo>='20';Workitem.ToDo<='30'))", o.getToken());
    }

    @Test
    public void NestedEmptyResult() throws APIException {
        OrFilterTerm o1 = new OrFilterTerm();
        OrFilterTerm o2 = new OrFilterTerm();
        AndFilterTerm a1 = new AndFilterTerm(o1, o2);
        AndFilterTerm a2 = new AndFilterTerm(a1);

        Assert.assertEquals(null, a2.getToken());
    }

    @Test
    public void singleNestedTerm() throws V1Exception {
        FilterTerm f1 = new FilterTerm(getWorkitemToDo());
        f1.greater(5);
        OrFilterTerm o1 = new OrFilterTerm(f1);
        OrFilterTerm o2 = new OrFilterTerm();
        AndFilterTerm a1 = new AndFilterTerm(o1, o2);
        AndFilterTerm a2 = new AndFilterTerm(a1);

        Assert.assertEquals("Workitem.ToDo>'5'", a2.getToken());
    }
}

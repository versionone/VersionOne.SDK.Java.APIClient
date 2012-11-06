package com.versionone.apiclient;

import com.versionone.apiclient.IAttributeDefinition.AttributeType;

public class FilterTerm implements IFilterTerm {
    public enum Operator {
        None,
        Equal,
        NotEqual,
        GreaterThan,
        LessThan,
        GreaterThanOrEqual,
        LessThanOrEqual,
        Exists,
        NotExists
    }

    private IAttributeDefinition def;
    private Operator operator = Operator.None;
    private IValueProvider valueProvider = new ValueProvider(new Object[0]);

    public FilterTerm(IAttributeDefinition def) {
        this.def = def;
    }

    public String getShortToken() throws APIException {
        return makeToken(false);
    }

    public String getToken() throws APIException {
        return makeToken(true);
    }

    private String makeToken(boolean full) throws APIException {
        if (operator == Operator.Exists) {
            if (!valueProvider.getValues().isEmpty()) {
                throw new APIException("Exists operator may not take values");
            }

            return "%2B" + def.getToken();
        }

        if (operator == Operator.NotExists) {
            if (!valueProvider.getValues().isEmpty()) {
                throw new APIException("NotExists operator may not take values");
            }

            return "-" + def.getToken();
        }

        if (valueProvider.getValues().isEmpty()) {
            return null;
        }

        String prefix = full ? def.getToken() : def.getName();
        return prefix + operatorToken(operator) + valueProvider.stringize();
    }

    public void equal(Object... value) {
        operate(Operator.Equal, value);
    }

    public void notEqual(Object... value) {
        operate(Operator.NotEqual, value);
    }

    public void greater(Object... value) {
        operate(Operator.GreaterThan, value);
    }

    public void less(Object... value) {
        operate(Operator.LessThan, value);
    }

    public void lessOrEqual(Object... value) {
        operate(Operator.LessThanOrEqual, value);
    }

    public void greaterOrEqual(Object... value) {
        operate(Operator.GreaterThanOrEqual, value);
    }

    public void exists() {
        operate(Operator.Exists);
    }

    public void notExists() {
        operate(Operator.NotExists);
    }

    public void operate(Operator newop, IValueProvider provider) {
        if (newop == Operator.GreaterThan || newop == Operator.GreaterThanOrEqual || newop == Operator.LessThan || newop == Operator.LessThanOrEqual) {
            AttributeType deftype = def.getAttributeType();

            if (deftype != AttributeType.Date && deftype != AttributeType.Numeric && deftype != AttributeType.Opaque) {
                throw new RuntimeException("Inequality Operation Not Valid For " + deftype.toString() + " AttributeType.");
            }
        }

        if (newop != operator) {
            operator = newop;

            if (provider != null) {
                valueProvider = provider;
            } else {
                valueProvider = new ValueProvider(new Object[0]);
            }

            return;
        }

        if (provider != null && valueProvider.canMerge() && provider.canMerge()) {
            valueProvider.merge(provider);
        }
    }

    public void operate(Operator newop, Object... value) {
        operate(newop, new ValueProvider(value));
    }

    private static String operatorToken(Operator op) {
        switch (op) {
            case Equal:
                return "=";
            case NotEqual:
                return "!=";
            case GreaterThan:
                return ">";
            case GreaterThanOrEqual:
                return ">=";
            case LessThan:
                return "<";
            case LessThanOrEqual:
                return "<=";
        }

        return null;
    }
}

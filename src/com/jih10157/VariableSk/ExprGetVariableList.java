package com.jih10157.VariableSk;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ExprGetVariableList extends SimpleExpression<String> {
    private Expression<String> string = null;
    private static Method getVariableHashMap;
    static {
        try {
            getVariableHashMap = Variables.class.getDeclaredMethod("getVariablesHashMap");
            getVariableHashMap.setAccessible(true);
            Skript.registerExpression(ExprGetVariableList.class, String.class, ExpressionType.SIMPLE, "variable list[ contain %-string%]");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected String[] get(Event event) {
        try {
            Set<String> set = ((Map<String, Object>)getVariableHashMap.invoke(null)).keySet();
            if(!(string==null)) {
                String s1 = string.getSingle(event);
                set = set.stream().filter(s2 -> s2.contains(s1)).collect(Collectors.toSet());
            }
            return set.toArray(new String[0]);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
        return new String[0];
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "Variable List" + (string==null? "":" Contain \""+string.toString(event, b));
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(expressions.length == 1) {
            string = (Expression<String>) expressions[0];
        }
        return true;
    }
}

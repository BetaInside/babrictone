package com.github.BetaInside.babrictone.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class manager {

    private boolean enabled = false;
    private static HashMap<Class<? extends manager>, manager> managers = new HashMap<Class<? extends manager>, manager>();
    public final static manager getManager(Class<? extends manager> c) {
        if (managers.get(c) == null) {
            try {
                managers.put(c, (manager) c.getMethod("createInstance", Class.class).invoke(null, c));
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(manager.class.getName()).log(Level.SEVERE, null, ex);
                managers.put(c, createInstance(c));
            } catch (SecurityException ex) {
                Logger.getLogger(manager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(manager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(manager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (managers.get(c) == null) {
            throw new RuntimeException("Wtf idek");
        }
        return managers.get(c);
    }
    public final static void tick(Class<? extends manager> c) {
        getManager(c).tick();
    }
    public final static void tick(Class<? extends manager> c, boolean prepost) {
        getManager(c).tick(prepost);
    }
    public final static boolean enabled(Class<? extends manager> c) {
        return getManager(c).enabled();
    }
    public final static void cancel(Class<? extends manager> c) {
        getManager(c).cancel();
    }
    public final static void start(Class<? extends manager> c) {
        getManager(c).start();
    }
    public final static boolean toggle(Class<? extends manager> c) {
        return getManager(c).toggle();
    }
    public static manager createInstance(Class c) {
        try {
            return (manager) c.newInstance();
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
    public final void tick() {
        this.tick((Boolean) null);
    }
    public final void tick(Boolean prepost) {
        //Out.log(this + " " + enabled());
        if (!enabled()) {
            return;
        }
        if (prepost == null) {
            onTick();
        } else if (prepost) {
            onTickPre();
        } else {
            onTickPost();
        }
    }
    public final boolean enabled() {
        return onEnabled(enabled);
    }
    public final void cancel() {
        enabled = false;
        onCancel();
    }
    public final void start() {
        enabled = true;
        onStart();
    }
    public final boolean toggle() {
        if (enabled()) {
            cancel();
        } else {
            start();
        }
        return enabled();
    }
    protected void onTickPre() {
    }
    protected void onTickPost() {
    }
    protected boolean onEnabled(boolean enabled) {
        return enabled;
    }
    protected abstract void onTick();
    protected abstract void onCancel();
    protected abstract void onStart();
}

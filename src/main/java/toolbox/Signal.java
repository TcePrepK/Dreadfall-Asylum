package toolbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Signal {
    private final HashMap<List<String>, List<Runnable>> listenerList = new HashMap<>();
    private final HashMap<List<String>, List<CustomRunnable>> customListenerList = new HashMap<>();

    public Signal() {
        listenerList.put(null, new ArrayList<>());
        customListenerList.put(null, new ArrayList<>());
    }

    public void add(final Runnable runnable, final String keys) {
        final List<String> keyCodes = keyCodeConverter(keys.toLowerCase());
        final List<Runnable> listeners = listenerList.get(keyCodes);
        listeners.add(runnable);
    }

    public void add(final Runnable runnable) {
        final List<Runnable> listeners = listenerList.get(null);
        listeners.add(runnable);
    }

    public void add(final CustomRunnable runnable) {
        final List<CustomRunnable> customListeners = customListenerList.get(null);
        customListeners.add(runnable);
    }

    public void dispatch() {
        for (final List<Runnable> listeners : listenerList.values()) {
            for (final Runnable listener : listeners) {
                listener.run();
            }
        }
    }

    public void dispatch(final Object arg) {
        for (final List<CustomRunnable> customListeners : customListenerList.values()) {
            for (final CustomRunnable listener : customListeners) {
                listener.run(arg);
            }
        }
    }

    public void testKeys() {
        for (final List<String> keyList : listenerList.keySet()) {
            if (keyList == null) {
                continue;
            }

            boolean available = true;
            for (final String key : keyList) {
                if (!Keyboard.isKeyDown(key)) {
                    available = false;
                    break;
                }
            }

            if (!available) {
                continue;
            }

            final List<Runnable> listeners = listenerList.get(keyList);
            for (final Runnable listener : listeners) {
                listener.run();
            }
        }
    }

    private List<String> keyCodeConverter(final String keyString) {
        final String[] keys = keyString.split("(?!^)");
        final List<String> keyList = Arrays.asList(keys);

        listenerList.computeIfAbsent(keyList, k -> new ArrayList<>());
        return keyList;
    }
}


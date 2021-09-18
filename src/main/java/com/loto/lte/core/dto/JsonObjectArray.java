package com.loto.lte.core.dto;
import java.math.BigDecimal;
import java.util.*;

public class JsonObjectArray extends ArrayList<LinkedHashMap<String, Object>>{
    private static final long serialVersionUID = 8154940219462381299L;

    private boolean nullToInitialize = false;

    public boolean isNullToInitialize() {
        return this.nullToInitialize;
    }

    public void setNullToInitialize(boolean nullToInitialize) {
        this.nullToInitialize = nullToInitialize;
    }

    public JsonObjectArray() {
        super();
    }

    public JsonObjectArray(List map) {
        super(map);
    }

    public Object[] getKeys() {
        Object[] keyArr = new Object[0];
        if (size() > 0) {
            keyArr = get(0).keySet().toArray();
        }
        return keyArr;
    }

    public void put(Object key, List l) {
        for (int i = 0; i < l.size(); i++) {
            if (size() < i + 1) {
                add(new JsonObject());
            }
            ((Map) get(i)).put(key, l.get(i));
        }
    }

    public void putAll(List m) {
        clear();
        addAll(m);
    }

    public void removeColumn(String key) {
        for (int i = 0; i < this.size(); i++) {

            if (this.get(i).containsKey(key)) {
                this.get(i).remove(key);
            }
        }
    }

    public void removeRow(int keyIndex) {
        remove(keyIndex);
    }

    public Object getKeyWithIndex(int keyIndex) {
        return getKeyWithIndex(keyIndex, 0);
    }

    public Object getKeyWithIndex(int keyIndex, int index) {
        Object retObj = null;
        Set<String> tempSet = this.get(index).keySet();

        if (keyIndex >= tempSet.size()) {
            System.out.println("\033[31;1m keyIndex >= tempSet.size() ===>> getKeyWithIndex( int keyIndex, int index ) \033[32;1;2m param: {" + keyIndex + ", " + index + "}! ");
        }

        Iterator<String> iterator = tempSet.iterator();
        for (int inx = 0; inx <= keyIndex; inx++) {
            retObj = iterator.next();
        }
        return retObj;
    }

    public List<JsonObject> get(Object key) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < size(); i++) {
            list.add(get(i).get(key));
        }
        return list;
    }

    public List<JsonObject> toListData() {
        List<JsonObject> listData = new ArrayList<JsonObject>();
        for (LinkedHashMap<String, Object> map : this) {
            listData.add(new JsonObject(map));
        }
        return listData;
    }

    public void addJsonObject(JsonObject Data) {
        add(new JsonObject(Data));
    }

    public void modify(String key, int index, Object replaceValue) {
        if (!(size() > index)) {
            System.out.println("\033[31;1m !( size() > index ) ===>> modify( String key, int index, String replaceValue ) \033[32;1;2m param: {" + key + ", " + index + ", " + replaceValue + "}! ");
        }
        if (!get(index).containsKey(key)) {
            System.out.println("\033[31;1m !get( index ).containsKey( key ) ===>> modify( String key, int index, String replaceValue ) \033[32;1;2m param: {" + key + ", " + index + ", " + replaceValue + "}! ");
        }
        get(index).put(key, replaceValue);
    }

    public void modifyBoolean(String key, int index, boolean replaceValue) {
        if (!(size() > index)) {
            System.out.println("\033[31;1m !( size() > index ) ===>> modifyBoolean( String key, int index, String replaceValue ) \033[32;1;2m param: {" + key + ", " + index + ", " + replaceValue + "}! ");
        }
        if (!get(index).containsKey(key)) {
            System.out.println("\033[31;1m !get( index ).containsKey( key ) ===>> modifyBoolean( String key, int index, String replaceValue ) \033[32;1;2m param: {" + key + ", " + index + ", " + replaceValue + "}! ");
        }
        get(index).put(key, replaceValue);
    }

    public void modifyString(String key, int index, String replaceValue) {
        if (!(size() > index)) {
            System.out.println("\033[31;1m !( size() > index ) ===>> modifyString( String key, int index, String replaceValue ) \033[32;1;2m param: {" + key + ", " + index + ", " + replaceValue + "}! ");
        }
        get(index).put(key, replaceValue);
    }

    public void modifyInt(String key, int index, int replaceValue) {
        if (!(size() > index)) {
            System.out.println("\033[31;1m !( size() > index ) ===>> modifyInt( String key, int index, int replaceValue ) \033[32;1;2m param: {" + key + ", " + index + ", " + replaceValue + "}! ");
        }
        get(index).put(key, replaceValue);
    }

    public void modifyDouble(String key, int index, double replaceValue) {
        if (!(size() > index)) {
            System.out.println("\033[31;1m !( size() > index ) ===>> modifyDouble( String key, int index, double replaceValue ) \033[32;1;2m param: {" + key + ", " + index + ", " + replaceValue + "}! ");
        }
        get(index).put(key, replaceValue);
    }

    public void modifyFloat(String key, int index, float replaceValue) {
        if (!(size() > index)) {
            System.out.println("\033[31;1m !( size() > index ) ===>> modifyFloat( String key, int index, float replaceValue ) \033[32;1;2m param: {" + key + ", " + index + ", " + replaceValue + "}! ");
        }
        get(index).put(key, replaceValue);
    }

    public void modifyLong(String key, int index, long replaceValue) {
        if (!(size() > index)) {
            System.out.println("\033[31;1m !( size() > index ) ===>>modifyLong( String key, int index, long replaceValue ) \033[32;1;2m param: {" + key + ", " + index + ", " + replaceValue + "}! ");
        }
        get(index).put(key, replaceValue);
    }

    public void modifyShort(String key, int index, short replaceValue) {
        if (!(size() > index)) {
            System.out.println("\033[31;1m !( size() > index ) ===>>modifyShort( String key, int index, BigDecimal replaceValue ) \033[32;1;2m param: {" + key + ", " + index + ", " + replaceValue + "}! ");
        }
        get(index).put(key, replaceValue);
    }

    public void modifyBigDecimal(String key, int index, BigDecimal replaceValue) {
        if (!(size() > index)) {
            System.out.println("\033[31;1m !( size() > index ) ===>>modifyBigDecimal( String key, int index, BigDecimal replaceValue ) \033[32;1;2m param: {" + key + ", " + index + ", " + replaceValue + "}! ");
        }
        get(index).put(key, replaceValue);
    }

    public void add(String key, Object value) {
        boolean add = false;
        for (int i = 0; i < size(); i++) {
            if (!get(i).containsKey(key)) {
                get(i).put(key, value);
                add = true;
                break;
            }
        }
        if (!add) {
            JsonObject row = new JsonObject();
            //row.set( key, value );
            add(row);
        }
    }

    public void add(String key, List<Object> value) {
        boolean add = false;
        for (int i = 0; i < size(); i++) {
            if (!get(i).containsKey(key)) {
                get(i).put(key, value);
                add = true;
                break;
            }
        }
        if (!add) {
            JsonObject row = new JsonObject();
            //row.set( key, value );
            add(row);
        }
    }

    private Object getObject(Object key, int index) {

        try {
            if (size() <= index) {
                return null;
            } else {
                return get(index).get(key);
            }
        } catch (IndexOutOfBoundsException ioe) {
            throw ioe;
        }
    }

    public Object get(Object key, int index) {
        Object o = getObject(key, index);

        if (o == null && isNullToInitialize()) {
            return "";
        }

        return o;
    }

    public Object get(int keyIndex, int index) {
        Object key = getKeyWithIndex(keyIndex, index);
        return get(key, index);
    }

    public String getString(Object key, int index) {
        Object o = getObject(key, index);

        if (o == null) {
            if (isNullToInitialize()) {
                return "";
            }
            return null;
        } else {
            // If the key type is BigDecimal, it is represented as 0E-8 when the decimal point is set at least 8 digits.
            if (o instanceof BigDecimal) {
                return ((BigDecimal) o).toPlainString();
            } else {
                return o.toString();
            }
        }
    }

    public String getString(int keyIndex, int index) {
        Object key = getKeyWithIndex(keyIndex, index);
        return getString(key, index);
    }

    public boolean getBoolean(Object key, int index) {
        Object o = getObject(key, index);

        if (o == null) {
            if (isNullToInitialize()) {
                return false;
            }
        } else {
            if (o instanceof Boolean) {
                return ((Boolean) o).booleanValue();
            }
            if (o instanceof String) {
                try {
                    return Boolean.valueOf(o.toString()).booleanValue();
                } catch (Exception e) {
                    System.out.println("\033[31;1m Exception ===>> getInt(Object key, int index) \033[32;1;2m param: {" + key + ", " + index + "}! ");
                    throw e;
                }
            }
        }
        return false; // prevent compile error line. unreachable block.
    }

    public boolean getBoolean(int keyIndex, int index) {
        Object key = getKeyWithIndex(keyIndex, index);
        return getBoolean(key, index);
    }

    public int getInt(Object key, int index) {
        Object o = getObject(key, index);

        if (o == null) {
            if (isNullToInitialize()) {
                return 0;
            }
        } else {
            if (o instanceof Number) {
                return ((Number) o).intValue();
            }
            if (o instanceof String) {
                try {
                    return Integer.parseInt(o.toString());
                } catch (Exception e) {
                    System.out.println("\033[31;1m Exception ===>> getInt(Object key, int index) \033[32;1;2m param: {" + key + ", " + index + "}! ");
                    throw e;
                }
            }
        }
        return 0; // prevent compile error line. It's unreachable block.
    }

    public int getInt(int keyIndex, int index) {
        Object key = getKeyWithIndex(keyIndex, index);
        return getInt(key, index);
    }

    public double getDouble(Object key, int index) {
        Object o = getObject(key, index);

        if (o == null) {
            if (isNullToInitialize()) {
                return 0.0D;
            }
        } else {
            if (o instanceof Number) {
                return ((Number) o).doubleValue();
            }
            if (o instanceof String) {
                try {
                    return Double.parseDouble(o.toString());
                } catch (Exception e) {
                    System.out.println("\033[31;1m Exception ===>> getDouble(Object key, int index) \033[32;1;2m param: {" + key + ", " + index + "}! ");
                    throw e;
                }
            }
        }
        return 0.0D; // prevent compile error line. unreachable block.
    }

    public double getDouble(int keyIndex, int index) {
        Object key = getKeyWithIndex(keyIndex, index);
        return getDouble(key, index);
    }

    public float getFloat(Object key, int index) {
        Object o = getObject(key, index);

        if (o == null) {
            if (isNullToInitialize()) {
                return 0.0F;
            }
            System.out.println("\033[31;1m o == null ===>> getFloat(Object key, int index) \033[32;1;2m param: {" + key + ", " + index + "}! ");
        } else {
            if (o instanceof Number) {
                return ((Number) o).floatValue();
            }
            if (o instanceof String) {
                try {
                    return Float.parseFloat(o.toString());
                } catch (Exception e) {
                    throw e;
                }
            }
            System.out.println("\033[31;1m else ===>> getFloat(Object key, int index) \033[32;1;2m param: {" + key + ", " + index + "}! ");
        }
        return 0.0F; // prevent compile error  line. unreachable block.
    }

    public float getFloat(int keyIndex, int index) {
        Object key = getKeyWithIndex(keyIndex, index);
        return getFloat(key, index);
    }

    public long getLong(Object key, int index) {
        Object o = getObject(key, index);

        if (o == null) {
            if (isNullToInitialize()) {
                return 0L;
            }
            System.out.println("\033[31;1m o == null ===>> getLong(Object key, int index) \033[32;1;2m param: {" + key + ", " + index + "}! ");
        } else {
            if (o instanceof Number) {
                return ((Number) o).longValue();
            }
            if (o instanceof String) {
                try {
                    return Long.parseLong(o.toString());
                } catch (Exception e) {
                    System.out.println("\033[31;1m Exception ===>> getLong(Object key, int index) \033[32;1;2m param: {" + key + ", " + index + "}! ");
                    throw e;
                }
            }
            System.out.println("\033[31;1m else ===>> getLong(Object key, int index) \033[32;1;2m param: {" + key + ", " + index + "}! ");;
        }
        return 0L; // prevent compile error line. unreachable block.
    }

    public long getLong(int keyIndex, int index) {
        Object key = getKeyWithIndex(keyIndex, index);
        return getLong(key, index);
    }

    public short getShort(Object key, int index) {
        Object o = getObject(key, index);

        if (o == null) {
            if (isNullToInitialize()) {
                return 0;
            }
            System.out.println("\033[31;1m isNullToInitialize() ===>> getShort(Object key, int index) \033[32;1;2m param: {" + key + ", " + index + "}! ");
        } else {
            if (o instanceof Number) {
                return ((Number) o).shortValue();
            }

            if (o instanceof String) {
                try {
                    return Short.parseShort(o.toString());
                } catch (Exception e) {
                    System.out.println("\033[31;1m getShort(Object key, int index) \033[32;1;2m param: {" + key + ", " + index + "}! ");
                    throw e;
                }
            }
        }
        return 0; // prevent compile error line. unreachable block.
    }

    public short getShort(int keyIndex, int index) {
        Object key = getKeyWithIndex(keyIndex, index);
        return getShort(key, index);
    }

    public BigDecimal getBigDecimal(Object key, int index) {
        Object o = getObject(key, index);

        if (o == null) {
            if (isNullToInitialize()) {
                return new BigDecimal(0);
            }
            return null;
        } else {
            if (o instanceof BigDecimal) {
                return (BigDecimal) o;
            }

            // NOTE
            if (o instanceof Number) {
                return new BigDecimal(((Number) o).doubleValue());
            }

            if (o instanceof String) {
                try {
                    return new BigDecimal((String) o);
                } catch (Exception e) {
                    System.out.println("\033[31;1m getBigDecimal(Object key, int index) \033[32;1;2m param: {" + key + ", " + index + "}! ");
                    throw e;
                }
            }
        }
        return new BigDecimal(0); // prevent compile error line. unreachable block.
    }

    public BigDecimal getBigDecimal(int keyIndex, int index) {
        Object key = getKeyWithIndex(keyIndex, index);
        return getBigDecimal(key, index);
    }

    public int getDataCount() {
        return this.size();

    }

    public JsonObject getData(int key) {
        Object obj = get(key);
        if (obj instanceof JsonObject) {
            return (JsonObject) obj;
        } else if (obj instanceof LinkedHashMap) {
            return new JsonObject((LinkedHashMap<String, Object>) obj);
        } else {
            return obj == null ? new JsonObject() : (JsonObject) obj;
        }
    }

    public void addJsonObjectArray(JsonObjectArray Data) {
        int cnt = Data.size();
        for (int i = 0; i < cnt; i++) {
            this.add(new JsonObject(Data.get(i)));
        }
    }

    public void addJsonObjectArrayDataNoClone(JsonObjectArray jsonObjectArray) {
        int cnt = jsonObjectArray.size();
        for (int i = 0; i < cnt; i++) {
            this.add(jsonObjectArray.get(i));
        }
    }

    public void addDataNoClone(JsonObject data) {
        add(data);
    }

    public int getKeyCount() {
        int keyCount = 0;
        if (size() > 0) keyCount = get(0).keySet().size();
        return keyCount;
    }

    public int getDataCount(Object key) {
        return this.size();
    }

    public boolean containsKey(Object key) {
        if (this.size() == 0) {
            return false;
        } else {
            return containsKey(0, key);
        }
    }

    public boolean containsKey(int index, Object key) {
        if (index >= this.size()) {
            return false;
        } else {
            return get(index).containsKey(key);
        }
    }
}

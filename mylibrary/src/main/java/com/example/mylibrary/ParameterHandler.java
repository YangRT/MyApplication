package com.example.mylibrary;
/*
 [0] @query("ip") String ip
 [1] @query("key") String key
 抽象类的自我实现类
 */
public abstract class ParameterHandler {

    abstract void apply(RequestBuilder builder,String value);

    static final class Query extends ParameterHandler{
        private String name;

        public Query(String name){
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            if(value == null) return;
            builder.addQueryParam(name,value);
        }
    }

    static final class Field extends ParameterHandler{
        private String name;

        public Field(String name){
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            if(value == null) return;
            builder.addFormField(name,value);
        }
    }
}

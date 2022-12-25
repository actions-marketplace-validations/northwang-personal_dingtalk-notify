package pers.northwang.dingtalknotify.func;

@FunctionalInterface
public interface RequestBuilder<T> {

  void params(T t) throws Exception;
}

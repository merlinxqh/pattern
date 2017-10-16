package com.xqh.juc.functional.methodRef;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

/**
 * Created by leo on 2017/10/16.
 */
public class Person {
    public enum Sex{
        MALE,FEMALE
    }

    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public Sex getGender() {
        return gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getName() {
        return name;
    }

    public static int compareByAge(Person a,Person b){
        return a.birthday.compareTo(b.birthday);
    }

    public static <T, SOURCE extends Collection<T>, DEST extends Collection<T>>
    DEST transferElements(SOURCE sourceColletions, Supplier<DEST> colltionFactory) {
        DEST result = colltionFactory.get();
        for (T t : sourceColletions) {
            result.add(t);
        }
        return result;
    }

    public static void main(String[] args) {
        Person[] persons=new Person[10];
        //使用匿名类
        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.birthday.compareTo(o2.birthday);
            }
        });

        //使用lambda表达式
        Arrays.sort(persons, ((o1,o2) -> o1.birthday.compareTo(o2.birthday)));

        //使用Lambda表达式和类的静态方法
        Arrays.sort(persons, ((o1, o2) -> Person.compareByAge(o1,o2)));

        //使用方法引用, 引用类的静态方法
        Arrays.sort(persons, Person::compareByAge);


        ComparisonProvider provider = new ComparisonProvider();

        //使用lambda表达式
        //对象的实例方法
        Arrays.sort(persons, (a,b) -> provider.compareByAge(a,b));

        //使用方法引用
        //引用的是对象的实例方法
        Arrays.sort(persons, provider::compareByAge);


        String[] stringsArray = {"Hello","World"};

        //使用lambda表达式和类型对象的实例方法.
        Arrays.sort(stringsArray, (a,b)-> a.compareToIgnoreCase(b));
        //使用方法引用
        //引用的是类型对象的实例方法
        Arrays.sort(stringsArray, String::compareToIgnoreCase);

        /**
         * 引用构造函数
         */
        final List<Person> personList=Arrays.asList(persons);

        //使用lambda表达式
        Set<Person> personSet = transferElements(personList, () -> new HashSet<>());


        Set<Person> personSet1 = transferElements(personList, HashSet::new);

    }
}
class ComparisonProvider{
    public int compareByName(Person a,Person b){
        return a.getName().compareTo(b.getName());
    }

    public int compareByAge(Person a,Person b){
        return a.getBirthday().compareTo(b.getBirthday());
    }
}



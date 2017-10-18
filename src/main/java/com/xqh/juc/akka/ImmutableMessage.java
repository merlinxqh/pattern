package com.xqh.juc.akka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by leo on 2017/10/18.
 * Actor之间传递的消息应该满足不可变性, 也就是不可变模式.
 * 因为可变的消息无法高效的在并发环境中使用.
 *   理论上Actor中的消息可以使用任何对象实例, 但是实际使用过程中 强烈建议使用不可变模式.
 */
public final class ImmutableMessage {
    private final int sequeueNumber;

    private final List<String> values;

    public ImmutableMessage(int sequeueNumber, List<String> values) {
        this.sequeueNumber = sequeueNumber;
        /**
         * 构造一个不可变List对象.
         */
        this.values = Collections.unmodifiableList(new ArrayList<>(values));
    }

    public int getSequeueNumber() {
        return sequeueNumber;
    }

    public List<String> getValues() {
        return values;
    }

    /**
     * 实际上,对于消息传递, 有以下3种策略.
     * 1. 称为至多一次投递. 在这种策略中,每条消息最多会被投递一次. 这种情况下 可能偶尔出现消息投递失败, 而导致消息丢失.
     * 2. 称为至少一次投递. 在这种策略中,每条消息最少会被投递一次, 知道投递成功为止. 因为在一些偶然的场合, 接受者可能会收到重复的消息, 但不会发生消息丢失.
     * 3. 称为精准的消息投递. 也就是所有的消息保证被精确地投递并成功投递一次. 既不会有丢失,也不会有重复接收.
     *
     * 很明显 第一种策略性能最好,成本最低. 第二种需要保存消息投递的状态并不断充实. 第三种策略则是成本最高企且最不容易实现的.
     *
     *  消息投递的可靠性没有必要再Akka层做保证, 消息的可靠性更应该在应用的业务层去维护.
     *
     *  此外Akka对消息投递可以在一定程度上保证顺序性.
     *    Actor A1 向 A2 顺序发送M1,M2,M3三条消息
     *    如果M1没有丢失,那么他一定先于M2和M3被A2收到.
     */
}

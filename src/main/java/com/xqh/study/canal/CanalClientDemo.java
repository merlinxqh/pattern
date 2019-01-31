package com.xqh.study.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author leo
 * @Date 2018/11/22 0022
 * canal demo
 * https://www.aliyun.com/jiaocheng/34834.html?spm=5176.100033.2.6.19372237dqKoCx
 */
public class CanalClientDemo {

	public static void main(String[] args) {
		CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",11111),"example","","");
		int batchSize = 1000;
		int emptyCount = 0;
		try {
			connector.connect();
			connector.subscribe("test_db\\..*");
			connector.rollback();
			int totalEntryCount = 1200;
			while (emptyCount < totalEntryCount) {
				Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
				long batchId = message.getId();
				int size = message.getEntries().size();
				if (batchId == -1 || size == 0) {
					emptyCount++;
					System.out.println("empty count : " + emptyCount);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					emptyCount = 0;
					printEntry(message.getEntries());
				}
				connector.ack(batchId); // 提交确认
			}
		} finally {
			connector.disconnect();
		}
	}
	private static void printEntry(List<Entry> entrys) {
		for (Entry entry : entrys) {
			if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
				continue;
			}
			CanalEntry.RowChange rowChage = null;
			try {
				rowChage = RowChange.parseFrom(entry.getStoreValue());
			} catch (Exception e) {
                //TODO
			}
			EventType eventType = rowChage.getEventType();
			System.out.println(String.format("binlog[%s:%s] , name[%s,%s] , eventType : %s",
					entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
					entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
					eventType));
			for (RowData rowData : rowChage.getRowDatasList()) {
				if (eventType == EventType.DELETE) {
					printColumn(rowData.getBeforeColumnsList());
				} else if (eventType == EventType.INSERT) {
					printColumn(rowData.getAfterColumnsList());
				} else {
					System.out.println("-------> before");
					printColumn(rowData.getBeforeColumnsList());
					System.out.println("-------> after");
					printColumn(rowData.getAfterColumnsList());
				}
			}
		}
	}
	private static void printColumn(List<Column> columns) {
		for (Column column : columns) {
			System.out.println(column.getName() + " : " + column.getValue() + " update=" + column.getUpdated());
		}
	}
}

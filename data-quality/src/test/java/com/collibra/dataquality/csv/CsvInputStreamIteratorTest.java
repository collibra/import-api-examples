package com.collibra.dataquality.csv;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Test;

import com.collibra.dataquality.fixtures.TestInputStreamProvider;

class CsvInputStreamIteratorTest {

	@Test
	void convertsFirstRow_whenHeaderIsSkipped() {
		//given
		InputStream resourceAsStream = TestInputStreamProvider.getInputStreamFromTestScores();
		CsvInputStreamIterator csvInputStreamIterator = new CsvInputStreamIterator(resourceAsStream);

		//when
		List<List<String>> content = IteratorUtils.toList(csvInputStreamIterator);

		//then
		assertThat(content).hasSize(1);
		assertThat(content.get(0)).hasSize(12)
				.containsExactly(
						"Test1 Metric Id",
						"Test1 Project Name",
						"Test1 Scorecard Name",
						"Test1 Score Name",
						"10",
						"50",
						"5",
						"40",
						"Test Max Time Created",
						"TAB_1_COLUMN_1",
						"Physical Domain",
						"DBs Community");
	}

}

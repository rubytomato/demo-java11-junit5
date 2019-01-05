package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.service.UserService;

// JUnit5のMockito拡張を有効にする
@ExtendWith(MockitoExtension.class)
@DisplayName("JUnit5 Test Demo")
public class JUnit5DemoTests {

  @Mock(answer = Answers.RETURNS_SMART_NULLS)
  UserService userService;

  // テストメソッドの前に実行される
  @BeforeEach
  // TestInfoを使用して、テストの表示名、テストクラス、テストメソッド、関連付けられているタグなど、現在のテストに関する情報を取得できます
  void setup(TestInfo testInfo) {
    System.out.println("setup: " + testInfo.getDisplayName());
  }

  // テストメソッドの後に実行される
  @AfterEach
  void tearDown() {
    System.out.println("tearDown");
  }

  @Test
  void userItems() {
    when(userService.getItems(eq(100L))).thenReturn(List.of("Apple", "Banana", "Cherry"));

    List<String> items = userService.getItems(100L);
    assertThat(items).hasSize(3);

    verify(userService).getItems(eq(100L));
  }

  @Test
  @DisplayName("demo1をテストする")
  // TestReporterを使用して現在のテスト実行に関する追加データを公開することができます
  // データはIDEによって表示されるかレポートに含まれるようになります
  void demo1(TestReporter reporter) {
    reporter.publishEntry("demo1のテスト");
    // demo1のテスト
    reporter.publishEntry("demo1 key", "demo1 value");
  }

  @Nested
  @DisplayName("Hoge Tests")
  class HogeTests {

    @RepeatedTest(value = 3, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("hogeをテストする")
    // RepetitionInfoを使用して、現在の繰り返しおよび対応する@RepeatedTestの繰り返しの総数に関する情報を取得できます
    void hoge(RepetitionInfo info) {
      System.out.println("hoge current:" + info.getCurrentRepetition() + " total:" + info.getTotalRepetitions());
    }

  }

  @Nested
  @DisplayName("Fuga Tests")
  class FugaTests {

    @ParameterizedTest
    @CsvSource({ "foo, 10000, 100, 2018-12-30T00:00:00.000", "bar, 20000, 200, 2018-12-30T12:00:00.000",
        "baz, 30000, 300, 2018-12-30T23:00:00.000" })
    @DisplayName("fugaをテストする")
    void fuga(String first, int second, long third, LocalDateTime fourth) {
      System.out
          .println("fuga first:" + first + " second:" + second + " third:" + third + " fourth:" + fourth.toString());
    }

  }

}

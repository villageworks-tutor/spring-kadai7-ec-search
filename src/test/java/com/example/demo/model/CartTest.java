package com.example.demo.model;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.example.demo.entity.Item;
import com.example.demo.entity.ItemEx;

class CartTest {
	
	Cart sut;

	@BeforeEach
	void setUp() throws Exception {
		sut = new Cart();
	}
	
	@Nested
	@DisplayName("Cart#clear()メソッドのテストクラス")
	class ClearTest {
		
		@ParameterizedTest
		@MethodSource("paramsProvider")
		void test(List<ItemEx> targetList) {
			// setup
			for (ItemEx target : targetList) {
				sut.add(target);
			}
			// execute
			sut.clear();
			// verify
			int actual = sut.getItems().size();
			assertThat(actual, is(0));
		}
		
		static Stream<Arguments> paramsProvider() {
			// setup
			List<ItemEx> targetList = null;
			List<List<ItemEx>> targets = new ArrayList<>();
			// 複数の商品が入っているカートを初期化する
			targetList = new ArrayList<>();
			targetList.add(new ItemEx(1, 1, "Javaの基本", 2500));
			targetList.add(new ItemEx(4, 2, "なつかしのアニメシリーズ", 2000));
			targetList.add(new ItemEx(7, 3, "パズルゲーム", 780));
			targets.add(targetList);
			
			// パラメータを返却
			return Stream.of(
					Arguments.of(targets.get(0))
					);
		}
		
	}
	
	@Nested
	@DisplayName("Cart#delete(Item)メソッドのテストクラス")
	class DeleteTest {
		
		@ParameterizedTest
		@MethodSource("paramsProvider")
		void test01(List<ItemEx> targetList, Integer targetId, List<ItemEx> expectedList) {
			// setup
			for (ItemEx target : targetList) {
				sut.add(target);
			}
			// execute
			sut.delete(targetId);
			// verify
			List<Item> actualList = sut.getItems();
			for (int i = 0; i < actualList.size(); i++) {
				String actual = actualList.get(i).toString();
				String expected = expectedList.get(i).toString();
				assertThat(actual, is(expected));
			}
		}
		
		static Stream<Arguments> paramsProvider() {
			// setup
			List<ItemEx> targetList = null;
			List<List<ItemEx>> targets = new ArrayList<>();
			List<Integer> id = new ArrayList<>();
			List<ItemEx> expectedList = null;
			List<List<ItemEx>> expected = new ArrayList<>();
			// Test-01: Javaの基本、Space Wars 3、Play the BasketBallが入っているカートから商品番号6の商品を削除する
			targetList = new ArrayList<>();
			targetList.add(new ItemEx(1, 1, "Javaの基本", 2500, 2));
			targetList.add(new ItemEx(6, 2, "Space Wars 3", 1000, 5));
			targetList.add(new ItemEx(9, 3, "Play the BasketBall", 2200, 3));
			targets.add(targetList);
			id.add(6);
			expectedList = new ArrayList<>();
			expectedList.add(new ItemEx(1, 1, "Javaの基本", 2500, 2));
			expectedList.add(new ItemEx(9, 3, "Play the BasketBall", 2200, 3));
			expected.add(expectedList);
			// Test-01: Javaの基本、Space Wars 3、Play the BasketBallが入っているカートから商品番号9の商品を削除する
			targetList = new ArrayList<>();
			targetList.add(new ItemEx(1, 1, "Javaの基本", 2500, 2));
			targetList.add(new ItemEx(6, 2, "Space Wars 3", 1000, 5));
			targetList.add(new ItemEx(9, 3, "Play the BasketBall", 2200, 3));
			targets.add(targetList);
			id.add(9);
			expectedList = new ArrayList<>();
			expectedList.add(new ItemEx(1, 1, "Javaの基本", 2500, 2));
			expectedList.add(new ItemEx(6, 2, "Space Wars 3", 1000, 5));
			expected.add(expectedList);
			// パラメータを返却
			return Stream.of(
					  Arguments.of(targets.get(0), id.get(0), expected.get(0))
					, Arguments.of(targets.get(1), id.get(1), expected.get(1))
					);
		}
		
	}

	@Nested
	@DisplayName("Cart#getTotalPrice()メソッドのテストクラス")
	class GetTotalPriceTest {
		
		@ParameterizedTest
		@MethodSource("paramsProvider")
		@DisplayName("カートの商品の合計金額を取得することができる")
		void test01(List<ItemEx> targetList, Integer expected) {
			// setup
			for (ItemEx target : targetList) {
				sut.add(target);
			}
			// verify
			Integer actual = sut.getTotalPrice();
			assertThat(actual, is(expected));
		}
		
		static Stream<Arguments> paramsProvider() {
			// setup
			List<ItemEx> targetList = null;
			List<List<ItemEx>> targets = new ArrayList<>();
			List<Integer> expected = new ArrayList<>();
			// Test-01: Javaの基本2冊が入っているカートの合計金額は5000円である
			targetList = new ArrayList<>();
			targetList.add(new ItemEx(1, 1, "Javaの基本", 2500, 1));
			targetList.add(new ItemEx(1, 1, "Javaの基本", 2500, 1));
			targets.add(targetList);
			expected.add(5000);
			// Test-02: Javaの基本3冊とパズルゲーム2個が入っているカートの合計金額は9060円である
			targetList = new ArrayList<>();
			targetList.add(new ItemEx(1, 1, "Javaの基本", 2500, 3));
			targetList.add(new ItemEx(7, 3, "パズルゲーム", 780, 2));
			targets.add(targetList);
			expected.add(9060);
			// パラメータを返却
			return Stream.of(
					  Arguments.of(targets.get(0), expected.get(0))
					, Arguments.of(targets.get(1), expected.get(1))
					);
		}
		
	}
	
	@Nested
	@DisplayName("Cart#add(Item)メソッドのテストクラス")
	class AddTest {

		@ParameterizedTest
		@MethodSource("paramsProvider")
		@DisplayName("カートに商品を追加することができる")
		void test01(List<ItemEx> targetList, List<ItemEx> expectedList) {
			// execute
			for (ItemEx target : targetList) {
				sut.add(target);
			}
			// verify
			List<Item> actualList = sut.getItems();
			for (int i = 0; i < actualList.size(); i++) {
				String actual = actualList.get(i).toString();
				String expected = expectedList.get(i).toString();
				assertThat(actual, is(expected));
			}
		}
		
		static Stream<Arguments> paramsProvider() {
			// setup
			List<ItemEx> targetList = null;
			List<List<ItemEx>> targets = new ArrayList<>();
			List<ItemEx> expectedList = null;
			List<List<ItemEx>> expected = new ArrayList<>();
			// Test-01: はじめての商品を追加する
			targetList = new ArrayList<>();
			targetList.add(new ItemEx(1, 1, "Javaの基本", 2500));
			targets.add(targetList);
			expectedList = new ArrayList<>();
			expectedList.add(new ItemEx(1, 1, "Javaの基本", 2500));
			expected.add(expectedList);
			// Test-02: Javaの基本が入っている状態でパズルゲームを追加できる
			targetList = new ArrayList<>();
			targetList.add(new ItemEx(1, 1, "Javaの基本", 2500));
			targetList.add(new ItemEx(7, 3, "パズルゲーム", 780));
			targets.add(targetList);
			expectedList = new ArrayList<>();
			expectedList.add(new ItemEx(1, 1, "Javaの基本", 2500));
			expectedList.add(new ItemEx(7, 3, "パズルゲーム", 780));
			expected.add(expectedList);
			// Test-03: Javaの基本とパズルゲームが入っている状態でパズルゲームを追加できる
			targetList = new ArrayList<>();
			targetList.add(new ItemEx(1, 1, "Javaの基本", 2500, 1));
			targetList.add(new ItemEx(7, 3, "パズルゲーム", 780, 2));
			targetList.add(new ItemEx(7, 3, "パズルゲーム", 780, 1));
			targets.add(targetList);
			expectedList = new ArrayList<>();
			expectedList.add(new ItemEx(1, 1, "Javaの基本", 2500, 1));
			expectedList.add(new ItemEx(7, 3, "パズルゲーム", 780, 3));
			expected.add(expectedList);
			
			// パラメータを返却
			return Stream.of(
					  Arguments.of(targets.get(0), expected.get(0))
					, Arguments.of(targets.get(1), expected.get(1))
					, Arguments.of(targets.get(2), expected.get(2))
					);
		}
	}
}

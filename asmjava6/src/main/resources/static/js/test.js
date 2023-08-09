const app = angular.module("shopping-cart-app", ['ngCookies']);
app.filter("vnCurrency", function() {
	return function(input) {
		if (isNaN(input)) return input;
		return currency(input, { symbol: "₫", separator: ",", precision: 0 }).format();
	};
});
app.controller("shopping-cart-ctrl", ['$scope', '$http', '$cookies', function($scope, $http, $cookies) {
	// Định nghĩa biến username và gán giá trị cho nó
	$scope.items = [];
	$scope.form = {};
	var username = $cookies.get('username');
	$scope.fullName = $cookies.get('fullName').replaceAll("_", " ");


	if ($scope.fullName) {
		console.log("Họ và tên đã có trong cookie:", $scope.fullName);
	} else {
		console.log("Họ và tên không có trong cookie.");
	}

	console.log(username);
	$scope.initialize = function() {
		$http.get(`/rest/carts/userCart/${username}`).then(resp => {
			console.log('Dữ liệu từ API:', resp.data);
			const activeItems = resp.data.filter(item => item.active);
			console.log('Sản phẩm có isActive = true:', activeItems);

			if (activeItems.length >= 0) {
				$scope.items = activeItems;
			} else {
				console.log('Không có sản phẩm nào có isActive = true.');
			}
		});
	};


	// Khởi đầu
	$scope.initialize();

	// Thêm  mới 
	$scope.cart = {
		items: [],
		addToCart(ProductId) {
			var item = this.items.find(item => item.product.id == ProductId);

			if (item) {
				item.quantity += 1;
				this.saveToDatabase(ProductId, username, item.quantity);
			} else {
				$http.get(`/rest/carts/product/${ProductId}`).then(resp => { // Thay đổi tham số thành ProductId
					this.items.push(resp.data);
					this.saveToDatabase(ProductId, username, 1);

				});
			}
		},
		saveToDatabase(ProductId, username, quantity) {
			// Thay thế bằng cách lấy username từ người dùng sau khi đăng nhập
			var url = `/rest/carts/add-to-cart/${ProductId}/${username}/${quantity}`;

			$http.post(url, {}).then(response => {
				if (response.data && response.data.message === "Added to cart successfully!") {
					alert("Added to cart successfully!");
					this.loadCartItems(username); // Load lại danh sách sản phẩm trong giỏ hàng sau khi cập nhật
				} else {
					// Xử lý phản hồi thất bại (nếu cần)
				}
			}).catch(error => {
				// Xử lý lỗi (nếu cần)
				console.error(error);
			});
		},
		loadCartItems(username) {
			var url = `/rest/carts/userCart/${username}`;
			$http.get(url).then(response => {
				this.items = response.data;
			}).catch(error => {
				// Xử lý lỗi (nếu cần)
				console.error(error);
			});
		},
	};


	$scope.updateQty = function(item) {

		var url = `/rest/carts/update-quantity/${item.id}/${item.quantity}`;
		$http.put(url, {})
			.then(response => {
				console.log(response.data);
				// Load lại danh sách sản phẩm trong giỏ hàng sau khi cập nhật
				$scope.cart.loadCartItems(username);
			})
			.catch(error => {
				console.error(error);
			});
	};

	$scope.increaseQty = function(item) {
		item.quantity += 1;
		$scope.updateQty(item);
	};

	$scope.decreaseQty = function(item) {
		if (item.quantity > 1) {
			item.quantity -= 1;
			$scope.updateQty(item);
		}
	};
	$scope.getTotalQuantity = function() {
		let totalQuantity = 0;
		for (const item of $scope.items) {
			totalQuantity += item.quantity;
		}

		return totalQuantity;
	};

	$scope.getTotalPrice = function() {
		let totalPrice = 0;
		for (const item of $scope.items) {
			totalPrice += item.product.price * item.quantity;
		}
		return totalPrice;
	};
	$scope.removeItem = function(id) {
		$http.put(`/rest/carts/deleteCart/${id}`)
			.then(response => {
				// Xóa sản phẩm thành công, cập nhật lại danh sách sản phẩm trong giỏ hàng
				$scope.initialize(); // Gọi hàm loadCartItems để cập nhật danh sách giỏ hàng sau khi xóa
			})
			.catch(error => {
				console.error(error);
			});
	};

	$scope.cart.loadCartItems(username);
}]);

const app = angular.module("shopping-cart-app", []);

app.controller("shopping-cart-ctrl", function($scope, $http) {
	// Khởi tạo $scope.cart là một đối tượng rỗng
	$scope.cart = {
		items: [],
		add(id) {
			const item = this.items.find(item => item.id == id);
			if (item) {
				item.qty++;
				this.saveToDtabase();
			} else {
				$http.get(`/rest/products/${id}`).then(resp => {
					resp.data.qty = 1;
					this.items.push(resp.data);
					this.saveToDtabase();
				});
			}
		},
		favorite(id){
			var url = `/rest/products/add-to-favorite/${id}`;
			alert(url);
			alert("Yêu thích sản phẩm thành công")
            $http.post(url)
                .then(response => {
                    console.log(response.data);
                    
                    
                })
                .catch(error => {
                    console.error(error);
                });
	
		},
		saveToDatabase() {
			const json = angular.toJson(this.items);
			$http.post("/rest/cart", json).then(resp => {
				console.log("Cart data saved to database.");
			});
		},
		get count() {
			return this.items
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		get amount() {
			return this.items
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart")
			this.items = json ? JSON.parse(json) : [];
		},
		remove(id) {
			var index = this.items.findIndex(item => item.id == id);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		},
		clear() {
			this.items = []
			this.saveToLocalStorage();
		},
	};
	$scope.cart.loadFromLocalStorage()
	// Hàm tăng qty
	$scope.increaseQty = function(item) {
		item.qty++;
		$scope.cart.saveToLocalStorage();
	};
	// Hàm giảm qty
	$scope.decreaseQty = function(item) {
		if (item.qty > 1) {
			item.qty--;
			$scope.cart.saveToLocalStorage();
		}
	};
	// Hàm xử lý sự kiện khi checkbox được thay đổi
	$scope.updateToggleAll = function() {
		// Tính tổng số hàng đã được chọn (checked)
		var totalChecked = $scope.cart.items.filter(item => item.checked).length;

		// Kiểm tra nếu có ít nhất một hàng được chọn, hiển thị nút xóa
		$scope.showDeleteButton = totalChecked > 0;
	};
	$scope.order = {
		address: "",
		account: { username: $("#fullName").text() },
		get orderDetail() {
			return $scope.cart.items.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty
				}
			})
		},
		purchase() {
			var order = angular.copy(this);
			$http.post("/rest/orders", order).then(resp => {
				alert("Đặt hàng thành công!")
				$scope.cart.clear();
				location.href = "/order/detail/" + resp.data.id;
			}).catch(error => {
				alert("Đặt hàng Thất Bại!")
				console.log(error)
			})
		}
	}
	
});

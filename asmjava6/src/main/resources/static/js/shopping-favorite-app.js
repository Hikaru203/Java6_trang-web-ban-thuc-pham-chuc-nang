app.controller("shopping-cart-ctrl", function($scope, $http) {
	let accountId = null;
	// Hàm để lấy giá trị của một cookie
	function load_all() {
		$http.get("http://localhost:8080/rest/favorites")
			.then(response => {
				// Gán dữ liệu vào biến favoriteProducts để hiển thị
				$scope.favoriteProducts = response.data;
				console.log($scope.favoriteProducts);
				const userCookie = getCookie("username");
				console.log(userCookie);
				$http.get(`http://localhost:8080/rest/accounts/${userCookie}`)
					.then(accountResponse => {
						accountId = accountResponse.data.id;
						console.log(accountId);

						// Tạo mảng mới chứa các sản phẩm có userId trùng với accountId
						$scope.filteredFavoriteProducts = $scope.favoriteProducts.filter(product => product.account.id === accountId);
						console.log($scope.filteredFavoriteProducts);
						$scope.favoriteProducts = $scope.filteredFavoriteProducts;

					})
					.catch(error => {
						// Xử lý lỗi ở đây nếu cần
					});

			})
			.catch(error => {
				console.error(error);
			});
	}
	function getCookie(name) {
		const value = `; ${document.cookie}`;
		const parts = value.split(`; ${name}=`);

		if (parts.length === 2) {
			return parts.pop().split(';').shift();
		}

		return null;
	}
	$scope.removeFromFavorites = function(productId) {
		const userCookie = getCookie("username");

		if (userCookie) {
			// Gọi API để tìm accountId dựa trên username
			$http.get(`http://localhost:8080/rest/accounts/${userCookie}`)
				.then(accountResponse => {
					const accountId = accountResponse.data.id;
              		const url = `http://localhost:8080/rest/products/remove/favorite-product/${productId}/${accountId}`;
					
					$http.delete(url)
						.then(response => {
							alert("Xoá thành công")
							console.log(response.data);
							// Sau khi xóa thành công, cập nhật lại danh sách sản phẩm yêu thích
							load_all();
						})
						.catch(error => {
							console.error(error);
						});
				})
				.catch(error => {
					console.error(error);
				});
		} else {
			console.log("Không tìm thấy cookie username.");
		}
	};
	$scope.cart = {
		items: [],
		favorite(id) {
			const userCookie = getCookie("username");
			console.log(userCookie);

			if (userCookie) {
				// Gọi API để tìm accountId dựa trên username
				$http.get(`http://localhost:8080/rest/accounts/${userCookie}`)
					.then(accountResponse => {
						accountId = accountResponse.data.id;
						const url = `http://localhost:8080/rest/products/add-to-favorite/${id}/${accountId}`;
						alert("Thêm sản phẩm yêu thích thành công!");
						$http.post(url)
							.then(response => {
								console.log(response.data);

							})
							.catch(error => {
								console.error(error);
							});
					})
					.catch(error => {
						console.error(error);
					});
			} else {
				console.log("Không tìm thấy cookie username.");
			}
		},
	};
	load_all();
	// Load danh sách sản phẩm yêu thích ban đầu
});
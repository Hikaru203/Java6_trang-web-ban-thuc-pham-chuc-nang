app.controller("reportFavorite-ctrl", function ($scope, $http, $window) {
    $scope.items = [];
    $scope.form = {};
    $scope.initialize = function () {
        // Load product data
        $http.get("/rest/Report/Favorite").then(resp => {
            $scope.items = resp.data;
    
        });
    };
    

    // Khởi đầu
    $scope.initialize();
    
})
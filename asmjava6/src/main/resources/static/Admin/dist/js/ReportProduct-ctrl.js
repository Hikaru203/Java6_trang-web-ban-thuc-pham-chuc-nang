app.controller("reportProduct-ctrl", function ($scope, $http, $window) {
    $scope.items = [];
    $scope.form = {};
    $scope.initialize = function () {
        // Load product data
        $http.get("/rest/Report/product").then(resp => {
            $scope.items = resp.data;
    
        });
    };
    

    // Khởi đầu
    $scope.initialize();
    
})
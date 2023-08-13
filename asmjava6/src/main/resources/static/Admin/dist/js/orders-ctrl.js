app.controller("order-ctrl", function ($scope, $http, $window) {
    $scope.items = [];
    $scope.form = {};
    $scope.formatTotalPrice = function(totalPrice) {
        return totalPrice.toFixed(2).replace(/\.?0+$/, '') + ' VND';
    }
    $scope.initialize = function () {
        // load brands
        $http.get("/rest/orders").then(resp => {
            $scope.items = resp.data;
            
            console.log($scope.items);
        });
    }

    // Khởi đầu
    $scope.initialize();

    $scope.pager = {
        page: 0,
        size: 5,
        get items() {
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },

        get count() {
            return Math.ceil(1.0 * $scope.items.length / this.size);
        },

        first() {
            this.page = 0;
        },
        prev() {
            this.page--;
            if(this.page <0){
                this.last();
            }
        },
        next() {
            this.page++;
            if(this.page >=this.count){
                this.first();
            }
        },
        last() {
            this.page = this.count-1;
        }
    }
})
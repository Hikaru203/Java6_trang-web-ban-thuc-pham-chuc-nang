app.controller("revenue-ctrl", function ($scope, $http, $window) {
    $scope.items = [];
    $scope.form = {};
    $scope.initialize = function () {
        // load brands
        $scope.formatTotalPrice = function(totalPrice) {
            return numeral(totalPrice).format('0,0') + ' VND';
        };
        
        $scope.formatDate = function(year, month) {
            const date = moment(`${year}-${month}`, 'YYYY-M').format('MM YYYY');
            return date.charAt(0).toUpperCase() + date.slice(1);
        };
        
        $http.get("/rest/Report").then(resp => {
            $scope.items = resp.data;
            // Extract data for chart
            const labels = $scope.items.map(item => $scope.formatDate(item.year, item.month));
            const data = $scope.items.map(item => item.totalRevenue);
            // Tính tổng doanh thu
            $scope.totalRevenue = $scope.items.reduce((total, item) => total + item.totalRevenue, 0);
            // Get the canvas element
            const canvas = document.getElementById("revenue-chart");
        
            // Create the chart using Chart.js
            const ctx = canvas.getContext("2d");
            new Chart(ctx, {
                type: "bar",
                data: {
                    labels: labels,
                    datasets: [{
                        label: "Tổng doanh thu/tháng",
                        data: data,
                        backgroundColor: "rgba(75, 192, 192, 0.2)",
                        borderColor: "rgba(75, 192, 192, 1)",
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                callback: function(value, index, values) {
                                    return $scope.formatTotalPrice(value);
                                }
                            }
                        }
                    }
                }
            });
        });
        
    }

    // Khởi đầu
    $scope.initialize();
    
})
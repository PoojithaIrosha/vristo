import {Component, OnInit} from '@angular/core';
import {Chart, registerables} from "chart.js";
import {DashboardService} from "./dashboard.service";
import {Product} from "../../dto/Product";

Chart.register(...registerables)

@Component({
    selector: 'app-dashboard-content',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

    topSellingProducts!: Product[];

    constructor(private dashboardService: DashboardService) {
    }

    ngOnInit() {
        this.renderSalesChart();
        this.renderOrderByCategoryChart();
        this.loadTopSellingProducts();
    }

    renderSalesChart() {
        new Chart("dailySalesChart", {
            type: 'line',
            data: {
                labels: ['Jan', 'Febr', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'],
                datasets: [{
                    label: 'Sales',
                    data: [12, 19, 3, 5, 2, 3],
                    borderWidth: 1
                }, {
                    label: 'Expenses',
                    data: [20, 9, 5, 7, 8, 11],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    renderOrderByCategoryChart() {
        new Chart("ordersByCategoryChart", {
            type: 'pie',
            data: {
                labels: ['Jan', 'Febr', 'Mar'],
                datasets: [{
                    label: '# of Votes',
                    data: [12, 19, 3],
                }]
            },
            options: {
                responsive: false,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    loadTopSellingProducts() {
        this.dashboardService.getTopSellingProducts().subscribe({
            next: (data) => {
                this.topSellingProducts = data;
            },
            error: err => {
                console.log(err);
            }
        });
    }
}

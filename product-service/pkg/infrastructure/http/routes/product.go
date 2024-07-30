package routes

import (
	"github.com/apexglobal/product-service/api/handlers"
	fa "github.com/apexglobal/product-service/pkg/application/factory"
)

func ProductRoutes(
	factory fa.ServiceFactory,
) []Route {
	srv := factory.Create().(fa.ProductService)
	handler := handlers.NewProductHandler(srv.Service)
	routes := []Route{
		{Method: "POST", Path: "/product", Handler: handler.CreateHandler()},
		{Method: "GET", Path: "/product/:id", Handler: handler.GetProductHandler()},
	}
	return routes
}

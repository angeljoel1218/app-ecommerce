package routes

import (
	fac "github.com/apexglobal/product-service/pkg/application/factory"
	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/mongo"
)

type Route struct {
	Method  string
	Path    string
	Handler gin.HandlerFunc
}

func NewRoutes(db *mongo.Database) []Route {
	var routes = ProductRoutes(fac.ProductServiceFactory{DB: db})
	return routes
}

package factory

import "go.mongodb.org/mongo-driver/mongo"

type ServiceFactory interface {
	Create() service
}

type ProductServiceFactory struct {
	DB *mongo.Database
}

func (p ProductServiceFactory) Create() service {
	ps := ProductService{db: p.DB}
	return ps.Create()
}

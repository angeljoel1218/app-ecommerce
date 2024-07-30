package factory

import "go.mongodb.org/mongo-driver/mongo"

type RepositoryFactory interface {
	Create() repository
}

type ProductRepositoryFactory struct {
	DB *mongo.Database
}

func (p ProductRepositoryFactory) Create() repository {
	pr := ProductRepository{db: p.DB}
	return pr.Create()
}

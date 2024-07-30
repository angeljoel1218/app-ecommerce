package main

import (
	"log"

	"github.com/apexglobal/product-service/cmd/bootstrap"
)

func main() {
	if err := bootstrap.Run(); err != nil {
		log.Fatal(err)
	}
}

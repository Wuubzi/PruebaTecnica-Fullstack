import { useEffect, useState } from 'react';

const productData = [
	{ id: 1, name: 'Laptop', price: 899 },
	{ id: 2, name: 'Teclado', price: 45 },
	{ id: 3, name: 'Mouse', price: 25 },
	{ id: 4, name: 'Monitor', price: 249 },
];

const fakeApi = {
	get(endpoint) {
		const search = new URL(endpoint, window.location.origin).searchParams
			.get('search')
			.toLowerCase();

		const products = productData.filter((product) =>
			product.name.toLowerCase().includes(search),
		);

		return Promise.resolve({ data: products });
	},
};

function ProductList({ api = fakeApi }) {
 const [products, setProducts] = useState([]);
 const [search, setSearch] = useState('');

 useEffect(() => {
		api.get(`/products?search=${encodeURIComponent(search)}`).then((response) => {
			setProducts(response.data);
		});
	}, [api, search]);

 return (
		<div>
			<input
				type="search"
				value={search}
				placeholder="Buscar producto"
				onChange={(event) => setSearch(event.target.value)}
			/>
			<ul>
				{products.map((product) => (
					<li key={product.id}>
						{product.name} - ${product.price}
					</li>
				))}
			</ul>
		</div>
 );
}

export default ProductList;

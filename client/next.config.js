/** @type {import('next').NextConfig} */
const nextConfig = {
  experimental: {
    appDir: false,    
  },
  images: {
    unoptimized: true,
  },
  // exportPathMap: async function () {
  //   return {
  //     '/': { page: '/' },
  //     '/manager/index': { page: '/manager' },
  //     '/worker/index': { page: '/worker' },
  //     '/login': { page: '/login' },
  //     '/signup': { page: '/signup' },
  //   };
  // },
};

module.exports = nextConfig;

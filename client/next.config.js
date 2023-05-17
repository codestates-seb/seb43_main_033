/** @type {import('next').NextConfig} */
const nextConfig = {
  experimental: {
    appDir: false,    
  },
  images: {
    unoptimized: true,
  },
};

module.exports = nextConfig;

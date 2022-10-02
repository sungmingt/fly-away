/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        pink: '#FFDADA',
        pale_pink: '#FFE5E5',
        green: '#A8F1DE',
        gray: '#696969',
        deep_green: '#02CD9A',
      },
      width: {
        768: '768px',
      },
      height: {
        768: '768px',
      },
    },
    maxWidth: {
      480: '480px',
    },
    screens: {
      s: { max: '480px' },
      sm: { max: '767px' },
      // => @media (max: '767px') { ... }
      md: { min: '768px' },
    },
    backgroundImage: {
      'split-green-pink': 'linear-gradient(to top, #FFDADA 65% , #A9F1DF 35%);',
    },
  },
  plugins: [],
};

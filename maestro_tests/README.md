# Maestro
[Maestro](https://maestro.mobile.dev/) is the simplest and most effective mobile UI testing framework, is a modern, straightforward, and highly 
efficient solution designed for rapid mobile UI testing.

This project includes 2 test flows:
- [search_product_test](search_product_test.yaml): This flow tries to search for an `Iphone` and a `nintendo` in the 
  main screen.
- [product_detail_test](product_detail_test.yaml): This flow search for an `Iphone 15` select one of the results and 
  validates components in the product detail.

To run Maestro you need to install the CLI first, check [here](https://maestro.mobile.dev/getting-started/installing-maestro), once you installed, 
you need to first run the app in the emulator, then you just need to tun the command `maestro test maestro_tests/` 
to run both test or `maestro test maestro_tests/TEST_FLOW_TO_RUN` to run one specific flow.

## 🎥 Test sample video


https://github.com/alejandro-rios/MeLi_challenge/assets/10689052/489cf51d-3902-41a8-aa1b-53730c79ecd1


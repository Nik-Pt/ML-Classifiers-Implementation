data = load('Data.txt');
files = {'4Centers.txt', '6Centers.txt', '8Centers.txt', '10Centers.txt', '12Centers.txt'};

x = data(:, 1);
y = data(:, 2);

for i = 1:length(files)
  figure;
  plot(x, y, '+', 'MarkerSize', 3);
  hold on;

  newData = load(files{i});
  numCenters = size(newData, 1);
  x_new = newData(:, 1);
  y_new = newData(:, 2);

  plot(x_new, y_new, '*', 'MarkerSize', 3, 'Color', 'r');

  xlabel('X-axis');
  ylabel('Y-axis');
  title(sprintf('Data Points from Data.txt with %d centers', numCenters));
  grid on;

  axis([-2 0 0 2]);
  xticks(-2:0.2:0);
  yticks(0:0.2:2);

  hold off;
end

dispersion = load('DispersionResults.txt');
k_values = [4, 6, 8, 10, 12];

figure;
plot(k_values, dispersion, '-o', 'MarkerSize', 8, 'LineWidth', 2, 'Color', 'b');

xlabel('Number of Centers');
ylabel('Dispersion');
title('Dispersion vs. Number of Centers for K-means');

grid on;




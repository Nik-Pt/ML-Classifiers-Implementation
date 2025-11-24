fileID = fopen('Data.txt', 'r');
data = textscan(fileID, '%f %f %s', 'Delimiter', ',', 'HeaderLines', 0);
fclose(fileID);


x1 = data{1};
x2 = data{2};
categories = data{3};

figure;
hold on;
grid on;

colors = {[0.5, 0, 0.5], 'g', 'r', 'b'};
labels = {'C1', 'C2', 'C3', 'C4'};

for i = 1:4
    category_idx = strcmp(categories, labels{i});

    plot(x1(category_idx), x2(category_idx), '+', 'Color', colors{i}, ...
        'DisplayName', labels{i}, 'MarkerSize', 8);
end

legend show;
xlabel('x1');
ylabel('x2');
title('Categorized Data Plot');

hold off;

